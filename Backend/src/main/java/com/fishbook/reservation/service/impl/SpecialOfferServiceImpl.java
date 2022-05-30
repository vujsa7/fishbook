package com.fishbook.reservation.service.impl;

import com.fishbook.additional.entity.information.model.AdditionalService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.ApiRequestException;
import com.fishbook.exception.DateTimeRangeOverlappingException;
import com.fishbook.reservation.dao.SpecialOfferRepository;
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

    private final EntityRepository entityRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(SpecialOffer specialOffer, Long entityId) {
        Entity entity = entityRepository.findById(entityId).orElseThrow(() -> new ApiRequestException("Entity with given id not found"));
        specialOffer.setEntity(entity);
        specialOffer.setPrice(calculatePrice(specialOffer));

        List<SpecialOffer> specialOffers = specialOfferRepository.findAllByEntity(entity);
        if (specialOffers.stream().anyMatch(s -> s.isOverlapping(specialOffer.getStartDateTime(), specialOffer.getEndDateTime()))) {
            throw new DateTimeRangeOverlappingException();
        }

        specialOfferRepository.save(specialOffer);
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
