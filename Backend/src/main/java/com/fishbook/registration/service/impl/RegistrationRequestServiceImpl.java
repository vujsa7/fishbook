package com.fishbook.registration.service.impl;

import com.fishbook.registration.dao.RegistrationRequestRepository;
import com.fishbook.registration.model.RegistrationRequest;
import com.fishbook.registration.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {
    private final RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    public RegistrationRequestServiceImpl(RegistrationRequestRepository registrationRequestRepository){
        this.registrationRequestRepository = registrationRequestRepository;
    }

    @Override
    public RegistrationRequest create(RegistrationRequest registrationRequest) {
        return registrationRequestRepository.save(registrationRequest);
    }

    @Override
    public List<RegistrationRequest> getAll() {
        return registrationRequestRepository.findAll();
    }

    @Override
    public Optional<RegistrationRequest> getById(Long id) {
        return registrationRequestRepository.findById(id);
    }

    @Override
    public void delete(RegistrationRequest request) {
        registrationRequestRepository.delete(request);
    }
}
