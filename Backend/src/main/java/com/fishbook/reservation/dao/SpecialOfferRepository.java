package com.fishbook.reservation.dao;

import com.fishbook.entity.model.Entity;
import com.fishbook.reservation.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {

    List<SpecialOffer> findAllByEntity(Entity entity);
    List<SpecialOffer> findAllByEntityId(Long id);
}
