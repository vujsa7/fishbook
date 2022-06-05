package com.fishbook.scheduling;

import com.fishbook.system.service.LoyaltyPointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final static long TWO_HOURS_MILLISECONDS = 7200000;
    private final LoyaltyPointsService loyaltyPointsService;

    @Scheduled(fixedDelay = TWO_HOURS_MILLISECONDS)
    public void updateLoyaltyPoints() {
        loyaltyPointsService.addLoyaltyPoints();
    }
}
