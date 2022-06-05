package com.fishbook.reservation.service.impl;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.exception.DateTimeRangeOverlappingException;
import com.fishbook.exception.EntityNotAvailableException;
import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.fishing.lesson.model.FishingLesson;
import com.fishbook.reservation.dao.EntityAvailabilityRepository;
import com.fishbook.reservation.dao.SellerAvailabilityRepository;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.dao.SpecialOfferRepository;
import com.fishbook.reservation.model.EntityAvailability;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.model.SellerAvailability;
import com.fishbook.reservation.model.SpecialOffer;
import com.fishbook.reservation.service.SpecialOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialOfferServiceImpl implements SpecialOfferService {

    private final SpecialOfferRepository specialOfferRepository;
    private final SellerReservationRepository reservationRepository;
    private final EntityRepository entityRepository;
    private final SellerAvailabilityRepository sellerAvailabilityRepository;
    private final EntityAvailabilityRepository entityAvailabilityRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(SpecialOffer specialOffer, Long entityId) {
        Entity entity = entityRepository.findById(entityId).orElseThrow(EntityNotFoundException::new);

        List<SpecialOffer> specialOffers = specialOfferRepository.findAllByEntity(entity);
        if (specialOffers.stream().anyMatch(s -> s.isOverlapping(specialOffer.getStartDateTime(), specialOffer.getEndDateTime()))) {
            throw new DateTimeRangeOverlappingException();
        }
        if (reservationRepository.findAllByEntityId(entityId).stream().anyMatch(r -> r.isOverlapping(specialOffer.getStartDateTime(), specialOffer.getEndDateTime()))) {
            throw new DateTimeRangeOverlappingException();
        }
        checkEntityAvailability(specialOffer, entity);

        specialOffer.setEntity(entity);
        specialOffer.setPrice(calculatePrice(specialOffer));
        specialOfferRepository.save(specialOffer);
    }

    private void checkEntityAvailability(SpecialOffer specialOffer, Entity entity) {
        if (entity instanceof FishingLesson) {
            SellerAvailability availability = sellerAvailabilityRepository.findAllBySellerId(entity.getOwner().getId()).stream()
                    .filter(specialOffer::inRange)
                    .findFirst()
                    .orElseThrow(EntityNotAvailableException::new);

            SellerAvailability newSellerAvailability = new SellerAvailability(specialOffer.getEndDateTime(), availability.getToDateTime(), entity.getOwner());
            availability.setToDateTime(specialOffer.getStartDateTime());
            sellerAvailabilityRepository.save(newSellerAvailability);
            sellerAvailabilityRepository.save(availability);
        } else {
            EntityAvailability availability = entityAvailabilityRepository.findAllByEntityId(entity.getId()).stream()
                    .filter(specialOffer::inRange)
                    .findFirst()
                    .orElseThrow(EntityNotAvailableException::new);

            EntityAvailability newEntityAvailability = new EntityAvailability(specialOffer.getEndDateTime(), availability.getToDateTime(), entity);
            availability.setToDateTime(specialOffer.getStartDateTime());
            entityAvailabilityRepository.save(newEntityAvailability);
            entityAvailabilityRepository.save(availability);
        }
    }

    @Override
    public List<SpecialOffer> getSpecialOffersByEntityId(Long id) {
        return specialOfferRepository.findAllByEntityId(id);
    }

    public List<SpecialOffer> getAll(Long entityId) {
        return specialOfferRepository.findAllByEntityId(entityId);
    }

    private Double calculatePrice(SpecialOffer specialOffer){
        Long numOfDays = ChronoUnit.DAYS.between(specialOffer.getStartDateTime(), specialOffer.getEndDateTime());
        Double initialPrice = numOfDays * specialOffer.getEntity().getPricePerDay();
        for(AdditionalService additionalService : specialOffer.getAdditionalServices()){
            initialPrice += additionalService.getPrice();
        }

        return initialPrice * ((100 - specialOffer.getDiscount()) / 100);
    }
}
