package com.fishbook.registration.controller;

import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.registration.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrationRequests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    @Autowired
    public RegistrationRequestController(RegistrationRequestService registrationRequestService){
        this.registrationRequestService = registrationRequestService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequest> createRegistrationRequest(@RequestBody RegistrationRequest registrationRequest) {
        RegistrationRequest req = registrationRequestService.create(registrationRequest);
        return new ResponseEntity<>(req, HttpStatus.CREATED);
    }
}
