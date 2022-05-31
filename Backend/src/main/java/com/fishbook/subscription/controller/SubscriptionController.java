package com.fishbook.subscription.controller;
import com.fishbook.subscription.dto.SubscriptionDto;
import com.fishbook.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity toggleSubscription(@RequestBody SubscriptionDto subscriptionDto, Principal principal){
        subscriptionService.toggleSubscription(principal.getName(), subscriptionDto.getEntityId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{entityId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity checkSubscriptionStatus(@PathVariable Long entityId, Principal principal){
        return new ResponseEntity(subscriptionService.isSubscribed(principal.getName(), entityId), HttpStatus.OK);
    }

}
