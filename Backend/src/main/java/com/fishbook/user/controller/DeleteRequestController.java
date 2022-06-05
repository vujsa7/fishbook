package com.fishbook.user.controller;

import com.fishbook.user.dto.DeleteRequestDto;
import com.fishbook.user.service.DeleteRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/deleteRequests")
@RequiredArgsConstructor
public class DeleteRequestController {

    private final DeleteRequestService deleteRequestService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DeleteRequestDto>> getAll() {
        List<DeleteRequestDto> deleteRequests = deleteRequestService.getAll().stream()
                .map(deleteRequest -> new DeleteRequestDto(deleteRequest.getId(), deleteRequest.getRequestMessage(),
                        deleteRequest.getUser().getId(), deleteRequest.getUser().getFirstName(), deleteRequest.getUser().getLastName(),
                        deleteRequest.getUser().getEmail(), deleteRequest.getUser().getPhoneNumber()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(deleteRequests, HttpStatus.OK);
    }
}
