package com.fishbook.registration.controller;

import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.registration.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrationRequests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    @Autowired
    public RegistrationRequestController(RegistrationRequestService registrationRequestService){
        this.registrationRequestService = registrationRequestService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegistrationRequest>> getAllRequests(){
        List<RegistrationRequest> requests = registrationRequestService.getAll();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> createRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        RegistrationRequest req = registrationRequestService.create(registrationRequest);
        return new ResponseEntity<>(req, HttpStatus.CREATED);
    }
}
