package com.fishbook.reservation.service.impl;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.boat.model.Boat;
import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.house.model.House;
import com.fishbook.reservation.dao.*;
import com.fishbook.reservation.model.*;
import com.fishbook.reservation.service.ClientReservationService;
import com.fishbook.system.service.ConfigService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
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
    private final ReservationReviewRepository reservationReviewRepository;
    private final EmailService emailService;
    private final SpecialOfferRepository specialOfferRepository;

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
            createFishingLessonReservation(reservationCandidate, entity);
        } else if(entity.getClass().getName().contains("Boat")){
            createBoatReservation(reservationCandidate, entity);
        } else {
            createHouseReservation(reservationCandidate);
        }
        if(calculatePriceForReservation(reservationCandidate, user).compareTo(reservationCandidate.getTotalPrice()) != 0)
            throw new ApiRequestException("Price doesn't match with our price in the system. Please try again.");

        Reservation reservation = new Reservation(reservationCandidate.getStart(), reservationCandidate.getEnd(), 0, reservationCandidate.getTotalPrice(),  new HashSet<>(reservationCandidate.getAdditionalServices()), entity, user);
        reservationRepository.save(reservation);

        try{
            sendConfirmationEmail(reservation, user);
        } catch (InterruptedException e) { e.printStackTrace(); }

        return reservationCandidate;
    }

    @Transactional
    @Override
    public void createReservationOnSpecialOffer(Long specialOfferId, String email) {
        Optional<SpecialOffer> specialOfferOptional = specialOfferRepository.findById(specialOfferId);
        if(specialOfferOptional.isEmpty())
            throw new ApiRequestException("Special offer is missing or is taken by someone else");
        User user = userRepository.findByEmail(email);
        SpecialOffer specialOffer = specialOfferOptional.get();
        LocalDateTime now = LocalDateTime.now();
        now.with(LocalTime.MIN);
        now.with(LocalTime.MIDNIGHT);
        if(specialOffer.getStartDateTime().isBefore(now))
            throw new ApiRequestException("Can't create reservation for that starting date.");
        Entity entity = specialOffer.getEntity();
        ReservationCandidate reservationCandidate = new ReservationCandidate(specialOffer.getStartDateTime(), specialOffer.getEndDateTime(), specialOffer.getPrice(),
                new ArrayList<>(specialOffer.getAdditionalServices()), email, entity.getId());
        if(entity.getClass().getName().contains("FishingLesson")){
            createFishingLessonReservation(reservationCandidate, entity);
        } else if(entity.getClass().getName().contains("Boat")){
            createBoatReservation(reservationCandidate, entity);
        } else {
            createHouseReservation(reservationCandidate);
        }

        Reservation reservation = new Reservation(reservationCandidate.getStart(), reservationCandidate.getEnd(), 0, reservationCandidate.getTotalPrice(),  new HashSet<>(reservationCandidate.getAdditionalServices()), entity, user);
        reservationRepository.save(reservation);
        specialOfferRepository.delete(specialOffer);

        try{
            sendConfirmationEmail(reservation, user);
        } catch (InterruptedException e) { e.printStackTrace(); }

    }

    @Override
    public List<Reservation> getReservationHistory(String email) {
        User user = userRepository.findByEmail(email);
        return reservationRepository.findAllByClientId(user.getId());
    }

    @Override
    public String getStatus(Reservation reservation) {
        if(reservation.getIsCancelled())
            return "Canceled";
        LocalDateTime now = LocalDateTime.now();
        now = now.with(LocalTime.MIN);
        now = now.with(LocalTime.MIDNIGHT);
        if(reservation.getEndDateTime().isBefore(now)) {
            if(reservation.getReservationReview() == null)
                return "Unrated";
            return "Completed";
        }
        if((reservation.getStartDateTime().isEqual(now) || reservation.getStartDateTime().isBefore(now)) && reservation.getEndDateTime().isAfter(now))
            return "Active";
        if(reservation.getStartDateTime().isAfter(now))
            return "Upcoming";
        return "Unknown";
    }

    @Transactional
    @Override
    public void cancelReservation(Long reservationId, String email) throws ApiRequestException {
        User client = userRepository.findByEmail(email);
        Reservation reservation = reservationRepository.findAllByClientId(client.getId()).stream().filter(r -> r.getId()==reservationId).findFirst().orElse(null);
        if(reservation == null)
            throw new ApiRequestException("Client doesn't seem to have specified reservation");
        LocalDateTime now = LocalDateTime.now();
        now = now.with(LocalTime.MIN);
        now = now.with(LocalTime.MIDNIGHT);
        if(reservation.getStartDateTime().isBefore(now.plusDays(3)))
            throw new ApiRequestException("Reservation is in 3 days, you can't cancel now.");
        reservation.setIsCancelled(true);
        reservationRepository.save(reservation);
        Entity entity = reservation.getEntity();
        if(entity instanceof FishingLesson)
            mergeSellerAvailabilities(reservation.getStartDateTime(), reservation.getEndDateTime(), entity.getOwner());
        else if(entity instanceof House)
            mergeEntityAvailabilities(reservation.getStartDateTime(), reservation.getEndDateTime(), entity);
        else if(entity instanceof Boat){
            AdditionalService skipperService = reservation.getAdditionalServices().stream().filter(a -> a.getName().equals("Skipper")).findFirst().orElse(null);
            if(skipperService != null){
                List<SellerUnavailability> sellerUnavailabilities = sellerUnavailabilityRepository.findSellerUnavailability(entity.getOwner().getId());
                SellerUnavailability unavailability = sellerUnavailabilities.stream().filter(u -> u.isEqual(reservation.getStartDateTime(), reservation.getEndDateTime())).findFirst().orElse(null);
                if(unavailability != null)
                    sellerUnavailabilityRepository.delete(unavailability);
            }
            mergeEntityAvailabilities(reservation.getStartDateTime(), reservation.getEndDateTime(), entity);
        }
        client.setPoints(client.getPoints()-5);
        userRepository.save(client);
    }

    @Override
    public void reviewReservation(Integer rating, String comment, String email, Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if(reservationOptional.isEmpty())
            throw new ApiRequestException("There is no reservation with that id.");
        Reservation reservation = reservationOptional.get();
        if(!reservation.getClient().getEmail().equals(email))
            throw new ApiRequestException("Bad request!");
        LocalDateTime now = LocalDateTime.now();
        now = now.with(LocalTime.MIDNIGHT);
        if(reservation.getEndDateTime().isAfter(now))
            throw new ApiRequestException("Reservation is not yet finished, you can't rate it.");
        Optional<ReservationReview> reservationReviewOptional = reservationReviewRepository.findOneByReservationId(reservationId);
        if(reservationReviewOptional.isPresent())
            throw new ApiRequestException("You have already rated this reservation, you can't do it again.");
        ReservationReview review = ReservationReview.builder().comment(comment).rating(rating).reservation(reservation).build();
        reservationReviewRepository.save(review);
    }

    private void mergeEntityAvailabilities(LocalDateTime reservationStart, LocalDateTime reservationEnd, Entity entity) {
        List<EntityAvailability> entityAvailabilities = entityAvailabilityRepository.findEntityAvailabilitiesToMerge(reservationStart.minusDays(1), reservationEnd.plusDays(1), entity.getId());
        if(entityAvailabilities.size() == 2){
            EntityAvailability availabilityBefore = entityAvailabilities.stream().filter(a -> a.getToDateTime().compareTo(reservationStart.minusDays(1))==0).findFirst().get();
            EntityAvailability availabilityAfter = entityAvailabilities.stream().filter(a -> a.getFromDateTime().compareTo(reservationEnd.plusDays(1))==0).findFirst().get();
            entityAvailabilityRepository.save(new EntityAvailability(availabilityBefore.getFromDateTime(), availabilityAfter.getToDateTime(), entity));
            entityAvailabilityRepository.delete(availabilityBefore);
            entityAvailabilityRepository.delete(availabilityAfter);
        } else if(entityAvailabilities.size() == 1){
            EntityAvailability entityAvailability = entityAvailabilities.get(0);
            if(entityAvailability.getFromDateTime().isBefore(reservationStart))
                entityAvailabilityRepository.save(new EntityAvailability(entityAvailability.getFromDateTime(), reservationEnd, entity));
            else if(entityAvailability.getToDateTime().isAfter(reservationEnd))
                entityAvailabilityRepository.save(new EntityAvailability(reservationStart, entityAvailability.getToDateTime(), entity));
            entityAvailabilityRepository.delete(entityAvailability);
        } else
            throw new RuntimeException("Something is wrong with availabilities");

    }

    private void mergeSellerAvailabilities(LocalDateTime reservationStart, LocalDateTime reservationEnd, User owner) {
        List<SellerAvailability> sellerAvailabilities = sellerAvailabilityRepository.findSellerAvailabilitiesToMerge(reservationStart.minusDays(1), reservationEnd.plusDays(1), owner.getId());
        if(sellerAvailabilities.size() == 2){
            SellerAvailability availabilityBefore = sellerAvailabilities.stream().filter(a -> a.getToDateTime().compareTo(reservationStart.minusDays(1))==0).findFirst().get();
            SellerAvailability availabilityAfter = sellerAvailabilities.stream().filter(a -> a.getFromDateTime().compareTo(reservationEnd.plusDays(1))==0).findFirst().get();
            sellerAvailabilityRepository.save(new SellerAvailability(availabilityBefore.getFromDateTime(), availabilityAfter.getToDateTime(), owner));
            sellerAvailabilityRepository.delete(availabilityBefore);
            sellerAvailabilityRepository.delete(availabilityAfter);
        } else if(sellerAvailabilities.size() == 1){
            SellerAvailability sellerAvailability = sellerAvailabilities.get(0);
            if(sellerAvailability.getFromDateTime().isBefore(reservationStart))
                sellerAvailabilityRepository.save(new SellerAvailability(sellerAvailability.getFromDateTime(), reservationEnd, owner));
            else if(sellerAvailability.getToDateTime().isAfter(reservationEnd))
                sellerAvailabilityRepository.save(new SellerAvailability(reservationStart, sellerAvailability.getToDateTime(), owner));
            sellerAvailabilityRepository.delete(sellerAvailability);
        } else
            throw new RuntimeException("Something is wrong with availabilities");
    }

    private void createFishingLessonReservation(ReservationCandidate reservationCandidate, Entity entity) {
        // Check SellerAvailability
        List<SellerAvailability> sellerAvailabilities = sellerAvailabilityRepository.findSellerAvailabilities(entity.getOwner().getId());
        SellerAvailability sellerAvailability = sellerAvailabilities.stream()
                .filter(a -> (a.getFromDateTime().isBefore(reservationCandidate.getStart()) || a.getFromDateTime().isEqual(reservationCandidate.getStart()))
                        && (a.getToDateTime().isAfter(reservationCandidate.getEnd()) || a.getToDateTime().isEqual(reservationCandidate.getEnd())))
                .findFirst().orElse(null);
        if(sellerAvailability == null)
            throw new ApiRequestException("Adventure is not available for reservation at that time. Please choose other dates.");
        // Intersect seller availability
        intersectSellerAvailability(sellerAvailability, reservationCandidate.getStart(), reservationCandidate.getEnd());
    }

    private void createBoatReservation(ReservationCandidate reservationCandidate, Entity entity) {
        List<EntityAvailability> boatAvailabilities = entityAvailabilityRepository.findEntityAvailabilities(reservationCandidate.getEntityId());
        EntityAvailability boatAvailability = boatAvailabilities.stream()
                .filter(a -> (a.getFromDateTime().isBefore(reservationCandidate.getStart()) || a.getFromDateTime().isEqual(reservationCandidate.getStart()))
                        && (a.getToDateTime().isAfter(reservationCandidate.getEnd()) || a.getToDateTime().isEqual(reservationCandidate.getEnd())))
                .findFirst().orElse(null);
        if(boatAvailability == null)
            throw new ApiRequestException("Boat is not available for reservation at that time. Please choose other dates.");
        AdditionalService skipperService = reservationCandidate.getAdditionalServices().stream().filter(a -> a.getName().equals("Skipper")).findFirst().orElse(null);
        if(skipperService != null){
            List<SellerUnavailability> sellerUnavailabilities = sellerUnavailabilityRepository.findSellerUnavailability(entity.getOwner().getId());
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


    private void createHouseReservation(ReservationCandidate reservationCandidate) {
        List<EntityAvailability> houseAvailabilities = entityAvailabilityRepository.findEntityAvailabilities(reservationCandidate.getEntityId());
        EntityAvailability houseAvailability = houseAvailabilities.stream()
                .filter(a -> (a.getFromDateTime().isBefore(reservationCandidate.getStart()) || a.getFromDateTime().isEqual(reservationCandidate.getStart()))
                        && (a.getToDateTime().isAfter(reservationCandidate.getEnd()) || a.getToDateTime().isEqual(reservationCandidate.getEnd())))
                .findFirst().orElse(null);
        if(houseAvailability == null)
            throw new ApiRequestException("House is not available for reservation at that time. Please choose other dates.");
        intersectEntityAvailability(houseAvailability, reservationCandidate.getStart(), reservationCandidate.getEnd());
    }


    private Double calculatePriceForReservation(ReservationCandidate reservationCandidate, User client) {
        long days = calculateDaysForReservation(reservationCandidate);
        Entity entity = entityRepository.getById(reservationCandidate.getEntityId());
        Double basePrice = entity.getPricePerDay() * days;
        for (AdditionalService additionalService: reservationCandidate.getAdditionalServices()) {
            basePrice += additionalService.getPrice();
        }
        Integer discount = configService.getClientDiscountPercentageForPoints(client.getPoints()).intValue();
        Double totalPrice = basePrice;
        if(discount > 0)
            totalPrice -= (basePrice * discount/100);
        Integer sellerFee = configService.getSellerExtraRevenueForPoints(entity.getOwner().getPoints()).intValue();
        if(sellerFee > 0)
            totalPrice += (basePrice * sellerFee/100);
        totalPrice += configService.getGlobalConfig().getSystemFee();
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

    private void sendConfirmationEmail(Reservation reservation, User user) throws InterruptedException {
        Email email = new Email("user.fishbook@gmail.com", "Reservation confirmation", "You have successfully reserved " + reservation.getEntity().getName() +
                " from " + reservation.getStartDateTime() + " to " + reservation.getEndDateTime());
        emailService.sendEmail(email);
    }

}
