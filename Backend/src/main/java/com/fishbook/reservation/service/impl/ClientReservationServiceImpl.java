package com.fishbook.reservation.service.impl;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.reservation.dao.EntityAvailabilityRepository;
import com.fishbook.reservation.dao.ReservationRepository;
import com.fishbook.reservation.dao.SellerAvailabilityRepository;
import com.fishbook.reservation.dao.SellerUnavailabilityRepository;
import com.fishbook.reservation.model.*;
import com.fishbook.reservation.service.ClientReservationService;
import com.fishbook.system.service.ConfigService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientReservationServiceImpl implements ClientReservationService {

    private final EntityRepository entityRepository;
    private final UserRepository userRepository;
    private final EntityAvailabilityRepository entityAvailabilityRepository;
    private final SellerUnavailabilityRepository sellerUnavailabilityRepository;
    private final SellerAvailabilityRepository sellerAvailabilityRepository;
    private final ConfigService configService;
    private final ReservationRepository reservationRepository;

    @Override
    public Entity getReservationOfferDetails(Long entityId) {
        Optional<Entity> entity = entityRepository.findById(entityId);
        if(entity.isPresent())
            return entity.get();
        else
            return null;
    }

    @Transactional
    @Override
    public ReservationCandidate createReservation(ReservationCandidate reservationCandidate) throws ApiRequestException {
        Optional<Entity> optionalEntity = entityRepository.findById(reservationCandidate.getEntityId());
        if(optionalEntity.isEmpty())
            return null;
        Entity entity = optionalEntity.get();
        User user = userRepository.findByEmail(reservationCandidate.getEmail());
        LocalDateTime now = LocalDateTime.now();
        now.with(LocalTime.MIN);
        now.with(LocalTime.MIDNIGHT);
        if(reservationCandidate.getStart().isBefore(now))
            throw new ApiRequestException("Can't create reservation for that starting date.");
        if(entity.getClass().getName().contains("FishingLesson")){
            createFishingLessonReservation(reservationCandidate, entity, user);
        } else if(entity.getClass().getName().contains("Boat")){
            createBoatReservation(reservationCandidate, entity, user);
        } else {
            createHouseReservation(reservationCandidate, entity, user);

        }
        // Check if price is right
        if(calculatePriceForReservation(reservationCandidate, user).compareTo(reservationCandidate.getTotalPrice()) != 0)
            throw new ApiRequestException("Price doesn't match with our price in the system. Please try again.");
        Reservation reservation = new Reservation(reservationCandidate.getStart(), reservationCandidate.getEnd(), 0, reservationCandidate.getTotalPrice(),  reservationCandidate.getAdditionalServices(), entity, user);
        reservationRepository.save(reservation);
        return reservationCandidate;
    }

    private void createFishingLessonReservation(ReservationCandidate reservationCandidate, Entity entity, User user) {
        // Check SellerAvailability
        List<SellerAvailability> sellerAvailabilities = sellerAvailabilityRepository.findAllBySellerId(entity.getOwner().getId());
        SellerAvailability sellerAvailability = sellerAvailabilities.stream()
                .filter(a -> (a.getFromDateTime().isBefore(reservationCandidate.getStart()) || a.getFromDateTime().isEqual(reservationCandidate.getStart()))
                        && (a.getToDateTime().isAfter(reservationCandidate.getEnd()) || a.getToDateTime().isEqual(reservationCandidate.getEnd())))
                .findFirst().orElse(null);
        if(sellerAvailability == null)
            throw new ApiRequestException("Adventure is not available for reservation at that time. Please choose other dates.");
        // Intersect seller availability
        intersectSellerAvailability(sellerAvailability, reservationCandidate.getStart(), reservationCandidate.getEnd());
    }

    private void createBoatReservation(ReservationCandidate reservationCandidate, Entity entity, User user) {
        List<EntityAvailability> boatAvailabilities = entityAvailabilityRepository.findAllByEntityId(reservationCandidate.getEntityId());
        EntityAvailability boatAvailability = boatAvailabilities.stream()
                .filter(a -> (a.getFromDateTime().isBefore(reservationCandidate.getStart()) || a.getFromDateTime().isEqual(reservationCandidate.getStart()))
                        && (a.getToDateTime().isAfter(reservationCandidate.getEnd()) || a.getToDateTime().isEqual(reservationCandidate.getEnd())))
                .findFirst().orElse(null);
        if(boatAvailability == null)
            throw new ApiRequestException("Boat is not available for reservation at that time. Please choose other dates.");
        AdditionalService skipperService = reservationCandidate.getAdditionalServices().stream().filter(a -> a.getName().equals("Skipper")).findFirst().orElse(null);
        if(skipperService != null){
            List<SellerUnavailability> sellerUnavailabilities = sellerUnavailabilityRepository.findAllBySellerId(entity.getOwner().getId());
            SellerUnavailability sellerUnavailability = sellerUnavailabilities.stream()
                    .filter(u -> u.isOverlapping(reservationCandidate.getStart(), reservationCandidate.getEnd()))
                    .findFirst().orElse(null);
            if(sellerUnavailability != null){
                throw new ApiRequestException("This seller is not available as a skipper at that time. Please choose other dates.");
            }
            sellerUnavailabilityRepository.save(new SellerUnavailability(reservationCandidate.getStart(), reservationCandidate.getEnd(), entity.getOwner()));
        }
        intersectEntityAvailability(boatAvailability, reservationCandidate.getStart(), reservationCandidate.getEnd());
    }


    private void createHouseReservation(ReservationCandidate reservationCandidate, Entity entity, User user) {
        List<EntityAvailability> houseAvailabilities = entityAvailabilityRepository.findAllByEntityId(reservationCandidate.getEntityId());
        EntityAvailability houseAvailability = houseAvailabilities.stream()
                .filter(a -> (a.getFromDateTime().isBefore(reservationCandidate.getStart()) || a.getFromDateTime().isEqual(reservationCandidate.getStart()))
                        && (a.getToDateTime().isAfter(reservationCandidate.getEnd()) || a.getToDateTime().isEqual(reservationCandidate.getEnd())))
                .findFirst().orElse(null);
        if(houseAvailability == null)
            throw new ApiRequestException("House is not available for reservation at that time. Please choose other dates.");
        intersectEntityAvailability(houseAvailability, reservationCandidate.getStart(), reservationCandidate.getEnd());
    }


    private Double calculatePriceForReservation(ReservationCandidate reservationCandidate, User user) {
        long days = calculateDaysForReservation(reservationCandidate);
        Entity entity = entityRepository.getById(reservationCandidate.getEntityId());
        Double totalPrice = entity.getPricePerDay() * days;
        for (AdditionalService additionalService: reservationCandidate.getAdditionalServices()) {
            totalPrice += additionalService.getPrice();
        }
        Integer discount = configService.getClientDiscountPercentageForPoints(user.getPoints()).intValue();
        if(discount > 0)
            totalPrice = totalPrice * (1 - discount/100);
        return totalPrice;
    }

    private long calculateDaysForReservation(ReservationCandidate reservationCandidate) {
        LocalDateTime reservationStart = reservationCandidate.getStart();
        LocalDateTime reservationEnd = reservationCandidate.getEnd();
        LocalDateTime start = LocalDateTime.of(reservationStart.getYear(), reservationStart.getMonth(), reservationStart.getDayOfMonth(), 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(reservationEnd.getYear(), reservationEnd.getMonth(), reservationEnd.getDayOfMonth(), 0, 0, 0);
        return Duration.between(start, end).toDays() + 1;
    }

    private void intersectSellerAvailability(SellerAvailability sellerAvailability, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        if(sellerAvailability.isContaining(reservationStart, reservationEnd)){
            sellerAvailabilityRepository.save(new SellerAvailability(sellerAvailability.getFromDateTime(), reservationStart.minusDays(1), sellerAvailability.getSeller()));
            sellerAvailabilityRepository.save(new SellerAvailability(reservationEnd.plusDays(1), sellerAvailability.getToDateTime(), sellerAvailability.getSeller()));
            sellerAvailabilityRepository.delete(sellerAvailability);
        } else if(sellerAvailability.isEqual(reservationStart, reservationEnd)){
            sellerAvailabilityRepository.delete(sellerAvailability);
        } else if(sellerAvailability.getFromDateTime().isEqual(reservationStart) && sellerAvailability.getToDateTime().isAfter(reservationEnd)){
            sellerAvailabilityRepository.save(new SellerAvailability(reservationEnd.plusDays(1), sellerAvailability.getToDateTime(), sellerAvailability.getSeller()));
            sellerAvailabilityRepository.delete(sellerAvailability);
        } else if(sellerAvailability.getToDateTime().isEqual(reservationEnd) && sellerAvailability.getFromDateTime().isBefore(reservationStart)){
            sellerAvailabilityRepository.save(new SellerAvailability(sellerAvailability.getFromDateTime(), reservationStart.minusDays(1), sellerAvailability.getSeller()));
            sellerAvailabilityRepository.delete(sellerAvailability);
        }
    }

    private void intersectEntityAvailability(EntityAvailability boatAvailability, LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        if(boatAvailability.isContaining(reservationStart, reservationEnd)){
            entityAvailabilityRepository.save(new EntityAvailability(boatAvailability.getFromDateTime(), reservationStart.minusDays(1), boatAvailability.getEntity()));
            entityAvailabilityRepository.save(new EntityAvailability(reservationEnd.plusDays(1), boatAvailability.getToDateTime(), boatAvailability.getEntity()));
            entityAvailabilityRepository.delete(boatAvailability);
        } else if(boatAvailability.isEqual(reservationStart, reservationEnd)){
            entityAvailabilityRepository.delete(boatAvailability);
        } else if(boatAvailability.getFromDateTime().isEqual(reservationStart) && boatAvailability.getToDateTime().isAfter(reservationEnd)){
            entityAvailabilityRepository.save(new EntityAvailability(reservationEnd.plusDays(1), boatAvailability.getToDateTime(), boatAvailability.getEntity()));
            entityAvailabilityRepository.delete(boatAvailability);
        } else if(boatAvailability.getToDateTime().isEqual(reservationEnd) && boatAvailability.getFromDateTime().isBefore(reservationStart)){
            entityAvailabilityRepository.save(new EntityAvailability(boatAvailability.getFromDateTime(), reservationStart.minusDays(1), boatAvailability.getEntity()));
            entityAvailabilityRepository.delete(boatAvailability);
        }
    }

}
