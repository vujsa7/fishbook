package com.fishbook.user.service.impl;

import com.fishbook.user.dao.DeleteAccountRequestRepository;
import com.fishbook.user.model.DeleteAccountRequest;
import com.fishbook.user.service.DeleteRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteRequestServiceImpl implements DeleteRequestService {

    private final DeleteAccountRequestRepository deleteAccountRequestRepository;


    @Override
    public List<DeleteAccountRequest> getAll() {
        return deleteAccountRequestRepository.findAll();
    }
}
