package com.fishbook.user.service;

import com.fishbook.user.dto.DeleteResponseDto;
import com.fishbook.user.model.DeleteAccountRequest;

import java.util.List;

public interface DeleteRequestService {

    List<DeleteAccountRequest> getAll();
    void handleRequest(DeleteResponseDto dto) throws InterruptedException;
}
