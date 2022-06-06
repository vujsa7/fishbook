package com.fishbook.reservation.service.impl;

import com.fishbook.entity.model.Entity;
import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.dto.CalculateRevenueDto;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.model.ReservationOptions;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final SellerReservationRepository reservationRepository;
    private final EntityRepository entityRepository;

    @Override
    public List<Reservation> getAll(Long entityId) {
        return reservationRepository.findAllByEntityId(entityId);
    }

    @Override
    public Reservation get(Long id) {
        return reservationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Integer getNumberOfReservations(Entity entity) {
        return reservationRepository.countAllByEntity(entity);
    }

    @Override
    public Double calculateRevenue(CalculateRevenueDto dto) {
        if(dto.getEntityId() == -1) {
            return reservationRepository.getEntityReservation(dto.getFromDateTime(), dto.getToDateTime())
                    .stream()
                    .mapToDouble(ReservationOptions::getPrice).sum();
        } else {
            return reservationRepository.getEntityReservation(dto.getEntityId(), dto.getFromDateTime(), dto.getToDateTime())
                    .stream()
                    .mapToDouble(ReservationOptions::getPrice).sum();
        }
    }

    @Override
    public Entity getReservationOfferDetails(Long entityId) {
        Optional<Entity> entity = entityRepository.findById(entityId);
        if(entity.isPresent())
            return entity.get();
        else
            return null;
    }
}
