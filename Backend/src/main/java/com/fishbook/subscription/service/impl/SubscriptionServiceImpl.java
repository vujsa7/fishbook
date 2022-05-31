package com.fishbook.subscription.service.impl;

import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.subscription.service.SubscriptionService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final EntityRepository entityRepository;

    @Override
    public void toggleSubscription(String email, Long entityId) {
        User user = userRepository.findByEmail(email);
        Entity entity = entityRepository.getById(entityId);
        if(!user.getSubscribedEntities().removeIf(e -> e.getId().equals(entityId))){
            user.getSubscribedEntities().add(entity);
        }
        userRepository.save(user);
    }

    @Override
    public boolean isSubscribed(String email, Long entityId) {
        User user = userRepository.findByEmail(email);
        return user.getSubscribedEntities().stream().filter(e -> e.getId().equals(entityId)).findFirst().isPresent();
    }

    @Override
    public List<Entity> getSubscribedEntities(String email) {
        return userRepository.findByEmail(email).getSubscribedEntities().stream().collect(Collectors.toList());
    }

}
