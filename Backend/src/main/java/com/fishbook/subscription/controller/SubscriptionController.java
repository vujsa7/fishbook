package com.fishbook.subscription.controller;
import com.fishbook.entity.model.Entity;
import com.fishbook.reservation.service.SpecialOfferService;
import com.fishbook.storage.service.StorageService;
import com.fishbook.subscription.dto.SubscriptionDetailsDto;
import com.fishbook.subscription.dto.SubscriptionDto;
import com.fishbook.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final StorageService storageService;
    private final SpecialOfferService specialOfferService;

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

    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity getSubscriptions(Principal principal){
        List<Entity> subscribedEntities = subscriptionService.getSubscribedEntities(principal.getName());
//        if(subscribedEntities.isEmpty())
//            return new ResponseEntity()

        List<SubscriptionDetailsDto> subscriptionDetailsDtoList = new ArrayList<>();

        for (Entity subscribedEntity: subscribedEntities){
            String typeName = subscribedEntity.getClass().getName();
            subscriptionDetailsDtoList.add( new SubscriptionDetailsDto(
                    storageService.getPriorityImageUrl(subscribedEntity.getImages()), subscribedEntity.getName(), typeName.substring(typeName.lastIndexOf(".") + 1),
                    specialOfferService.getSpecialOffersByEntityId(subscribedEntity.getId()).size(), subscribedEntity.getId()));
        }
        return new ResponseEntity(subscriptionDetailsDtoList, HttpStatus.OK);
    }

}
