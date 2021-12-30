package com.fishbook.registration.service;

import com.fishbook.registration.model.RegistrationRequest;

import java.util.List;

public interface RegistrationRequestService {
    RegistrationRequest create(RegistrationRequest registrationRequest);
    List<RegistrationRequest> getAll();
}