package com.fishbook.reservation.service;

import com.fishbook.reservation.model.SpecialOffer;

public interface SpecialOfferService {
    void save(SpecialOffer specialOffer, Long entityId);
}
