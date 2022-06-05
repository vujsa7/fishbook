package com.fishbook.user.service.impl;

import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.user.dao.DeleteAccountRequestRepository;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.dto.DeleteResponseDto;
import com.fishbook.user.model.DeleteAccountRequest;
import com.fishbook.user.model.User;
import com.fishbook.user.service.DeleteRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteRequestServiceImpl implements DeleteRequestService {

    private final DeleteAccountRequestRepository deleteAccountRequestRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    @Override
    public List<DeleteAccountRequest> getAll() {
        return deleteAccountRequestRepository.findAll();
    }

    @Override
    public void handleRequest(DeleteResponseDto dto) throws InterruptedException {
        DeleteAccountRequest deleteAccountRequest = deleteAccountRequestRepository.findById(dto.getRequestId())
                .orElseThrow(EntityNotFoundException::new);
        User user = deleteAccountRequest.getUser();

        if (dto.getApproved()) {
            user.setDeleted(true);
            userRepository.save(user);
            sendConfirmationEmail(dto.getResponse());
        } else {
            user.setEnabled(true);
            userRepository.save(user);
            sendRejectionEmail(dto.getResponse());
        }

        deleteAccountRequestRepository.delete(deleteAccountRequest);
    }

    private void sendConfirmationEmail(String content) throws InterruptedException {
        Email email = new Email("user.fishbook@gmail.com", "Account deletion", content);
        emailService.sendEmail(email);
    }

    private void sendRejectionEmail(String content) throws InterruptedException {
        Email email = new Email("user.fishbook@gmail.com", "Account deletion", content);
        emailService.sendEmail(email);
    }
}
