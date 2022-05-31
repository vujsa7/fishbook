package com.fishbook.subscription.service;

import com.fishbook.entity.model.Entity;

import java.util.List;

public interface SubscriptionService {

    void toggleSubscription(String email, Long entityId);
    boolean isSubscribed(String email, Long entityId);

    List<Entity> getSubscribedEntities(String name);
}
