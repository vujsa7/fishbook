package com.fishbook.scheduling;

import com.fishbook.system.service.LoyaltyPointsService;
import com.fishbook.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final static long TWO_HOURS_MILLISECONDS = 7200000;
    private final LoyaltyPointsService loyaltyPointsService;
    private final UserService userService;

    @Scheduled(fixedDelay = TWO_HOURS_MILLISECONDS)
    public void updateLoyaltyPoints() {
        loyaltyPointsService.addLoyaltyPoints();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetUsersPenalties() {
        userService.resetUsersPenalties();
    }

    @Scheduled(fixedDelay = TWO_HOURS_MILLISECONDS)
    public void banUsersWhoHaveThreePenalties(){
        userService.banUsersWhoHaveThreePenalties();
    }
}
