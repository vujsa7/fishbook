package com.fishbook.reservation.service.impl;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.*;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.reservation.dao.EntityAvailabilityRepository;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.dao.SellerAvailabilityRepository;
import com.fishbook.reservation.model.EntityAvailability;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.service.SellerReservationService;
import com.fishbook.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class SellerReservationServiceImpl implements SellerReservationService {

    private final SellerReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final EntityRepository entityRepository;
    private final SellerAvailabilityRepository sellerAvailabilityRepository;
    private final EntityAvailabilityRepository entityAvailabilityRepository;
    private final EmailService emailService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createReservation(Reservation reservation, Long clientId, Long entityId) throws InterruptedException {
        if (reservationRepository.getActiveReservation(clientId, entityId).isEmpty()) {
            throw new NoActiveReservationException();
        }
        if (reservationRepository.findAllByEntityId(entityId).stream().anyMatch(r -> r.isOverlapping(reservation))) {
            throw new DateTimeRangeOverlappingException();
        }
        Entity entity = entityRepository.findById(entityId).orElseThrow(EntityNotFoundException::new);
        checkEntityAvailability(reservation, entity);

        saveReservation(reservation, clientId, entity);
        sendConfirmationEmail(reservation);
    }

    private void sendConfirmationEmail(Reservation reservation) throws InterruptedException {
        Email email = new Email("user.fishbook@gmail.com", "Reservation confirmation", "You have successfully reserved " + reservation.getEntity().getName() +
                "from " + reservation.getStartDateTime() + " to " + reservation.getEndDateTime());
        emailService.sendEmail(email);
    }

    private void saveReservation(Reservation reservation, Long clientId, Entity entity) {
        reservation.setClient(userRepository.findById(clientId).orElseThrow(UserNotFoundException::new));
        reservation.setEntity(entity);
        reservation.setPrice(calculatePrice(reservation));
        reservation.setIsCancelled(false);
        reservationRepository.save(reservation);
    }

    private void checkEntityAvailability(Reservation reservation, Entity entity) {
        if (entity instanceof FishingLesson) {
            SellerAvailability availability = sellerAvailabilityRepository.findAllBySellerId(entity.getOwner().getId()).stream()
                    .filter(sellerAvailability -> reservation.inRange(sellerAvailability))
                    .findFirst()
                    .orElseThrow(EntityNotAvailableException::new);

            SellerAvailability newSellerAvailability = new SellerAvailability(reservation.getEndDateTime(), availability.getToDateTime(), entity.getOwner());
            availability.setToDateTime(reservation.getStartDateTime());
            sellerAvailabilityRepository.save(newSellerAvailability);
            sellerAvailabilityRepository.save(availability);
        } else {
            EntityAvailability availability = entityAvailabilityRepository.findAllByEntityId(entity.getId()).stream()
                    .filter(entityAvailability -> reservation.inRange(entityAvailability))
                    .findFirst()
                    .orElseThrow(EntityNotAvailableException::new);

            EntityAvailability newEntityAvailability = new EntityAvailability(reservation.getEndDateTime(), availability.getToDateTime(), entity);
            availability.setToDateTime(reservation.getStartDateTime());
            entityAvailabilityRepository.save(newEntityAvailability);
            entityAvailabilityRepository.save(availability);
        }
    }

    private Double calculatePrice(Reservation reservation){
        Long numOfDays = ChronoUnit.DAYS.between(reservation.getStartDateTime(), reservation.getEndDateTime());
        double basePrice = numOfDays * reservation.getEntity().getPricePerDay();

        return basePrice + reservation.getAdditionalServices().stream().mapToDouble(AdditionalService::getPrice).sum();
    }
}
