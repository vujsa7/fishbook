package com.fishbook.reservation.service;

import com.fishbook.reservation.controller.SpecialOfferController;
import com.fishbook.reservation.model.SpecialOffer;

import java.util.List;

public interface SpecialOfferService {
    void save(SpecialOffer specialOffer, Long entityId);
    List<SpecialOffer> getAll(Long entityId);
}
