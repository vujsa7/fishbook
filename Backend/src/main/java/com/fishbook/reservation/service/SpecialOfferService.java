package com.fishbook.reservation.service;

import com.fishbook.reservation.controller.SpecialOfferController;
import com.fishbook.reservation.model.SpecialOffer;

import java.util.List;

public interface SpecialOfferService {

    void save(SpecialOffer specialOffer, Long entityId);
    List<SpecialOffer> getSpecialOffersByEntityId(Long id);
    List<SpecialOffer> getAll(Long entityId);

    SpecialOffer getSpecialOfferById(Long specialOfferId);
}
