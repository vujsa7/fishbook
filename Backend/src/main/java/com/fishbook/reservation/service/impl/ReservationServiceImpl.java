package com.fishbook.reservation.service.impl;

import com.fishbook.entity.model.Entity;
import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.dto.CalculateRevenueDto;
import com.fishbook.reservation.model.Reservation;
import com.fishbook.reservation.model.ReservationOptions;
import com.fishbook.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final SellerReservationRepository reservationRepository;

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
        return reservationRepository.getEntityReservation(dto.getEntityId(), dto.getFromDateTime(), dto.getToDateTime())
                .stream()
                .mapToDouble(ReservationOptions::getPrice).sum();
    }
}
