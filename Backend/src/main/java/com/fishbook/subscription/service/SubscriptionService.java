package com.fishbook.subscription.service;

public interface SubscriptionService {

    void toggleSubscription(String email, Long entityId);
    boolean isSubscribed(String email, Long entityId);
}
