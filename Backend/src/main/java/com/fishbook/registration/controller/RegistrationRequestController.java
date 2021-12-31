package com.fishbook.registration.controller;

import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.registration.dto.RegistrationResponse;
import com.fishbook.registration.service.RegistrationRequestService;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrationRequests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public RegistrationRequestController(RegistrationRequestService registrationRequestService, UserService userService, EmailService emailService){
        this.registrationRequestService = registrationRequestService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegistrationRequest>> getAllRequests(){
        List<RegistrationRequest> requests = registrationRequestService.getAll();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> createRegistrationRequest(@RequestBody RegistrationRequest registrationRequest){
        RegistrationRequest req = registrationRequestService.create(registrationRequest);
        return new ResponseEntity<>(req, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> respondToRequest(@RequestBody RegistrationResponse registrationResponse, @PathVariable Long id) throws InterruptedException {
        Optional<RegistrationRequest> req = registrationRequestService.getById(id);
        if(req.isEmpty()){
            return new ResponseEntity<>("Request doesn't exist.", HttpStatus.BAD_REQUEST);
        }
        if(registrationResponse.getApproved()){
            userService.save(req.get());
        }
        Email email = new Email(req.get().getEmail(), "Registration response from Fishbook", registrationResponse.getResponse());
        emailService.sendEmail(email);
        registrationRequestService.delete(req.get());

        return new ResponseEntity<>("Request successfully deleted", HttpStatus.OK);
    }
}
