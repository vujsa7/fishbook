package com.fishbook.registration.service;

import com.fishbook.registration.model.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface RegistrationRequestService {
    RegistrationRequest create(RegistrationRequest registrationRequest);
    List<RegistrationRequest> getAll();
    Optional<RegistrationRequest> getById(Long id);
    void delete(RegistrationRequest request);
}