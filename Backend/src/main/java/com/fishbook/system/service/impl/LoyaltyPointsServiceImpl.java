package com.fishbook.system.service.impl;

import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.system.dao.GlobalConfigRepository;
import com.fishbook.system.dao.LoyaltyConfigRepository;
import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;
import com.fishbook.system.service.LoyaltyPointsService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoyaltyPointsServiceImpl implements LoyaltyPointsService {

    private final SellerReservationRepository reservationRepository;
    private final GlobalConfigRepository globalConfigRepository;
    private final LoyaltyConfigRepository loyaltyConfigRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addLoyaltyPoints() {
        List<Reservation> reservations = reservationRepository.findFinishedReservations();
        GlobalConfig globalConfig = globalConfigRepository.findAll().get(0);

        reservations.forEach(reservation -> {
                    User buyer = reservation.getClient();
                    buyer.addLoyaltyPoints(globalConfig.getBuyerPointsPerReservation());
                    userRepository.save(buyer);
                    User seller = reservation.getEntity().getOwner();
                    seller.addLoyaltyPoints(globalConfig.getSellerPointsPerReservation());
                    userRepository.save(seller);
                    reservation.setLoyaltyPointsAdded(true);
                    reservationRepository.save(reservation);
                });
    }

    @Override
    public Double getLoyaltyDiscount(Long userId) {
        Integer userPoints = userRepository.getById(userId).getPoints();
        List<LoyaltyConfig> loyaltyConfigs = loyaltyConfigRepository.findAll();
        for (int i = 0; i < loyaltyConfigs.size() - 1; i++) {
            if (loyaltyConfigs.get(i).getBuyerMinPoints() <= userPoints && loyaltyConfigs.get(i + 1).getBuyerMinPoints() > userPoints) {
                return loyaltyConfigs.get(i).getDiscount();
            }
        }

        return loyaltyConfigs.get(loyaltyConfigs.size() - 1).getDiscount();
    }

    @Override
    public Double getLoyaltyRevenue(Long userId) {
        Integer sellerPoints = userRepository.getById(userId).getPoints();
        List<LoyaltyConfig> loyaltyConfigs = loyaltyConfigRepository.findAll();
        for (int i = 0; i < loyaltyConfigs.size() - 1; i++) {
            if (loyaltyConfigs.get(i).getSellerMinPoints() <= sellerPoints && loyaltyConfigs.get(i + 1).getSellerMinPoints() > sellerPoints) {
                return loyaltyConfigs.get(i).getExtraRevenue();
            }
        }

        return loyaltyConfigs.get(loyaltyConfigs.size() - 1).getExtraRevenue();
    }
}
