package com.fishbook.reservation.dao;

import com.fishbook.entity.model.Entity;
import com.fishbook.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SellerReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r where r.client.id = :clientId and r.entity.id = :entityId and r.startDateTime <= current_timestamp and r.endDateTime >= current_timestamp and r.isCancelled = false")
    Optional<Reservation> getActiveReservation(Long clientId, Long entityId);

    @Query("select r from Reservation r where r.entity.id = :entityId and r.startDateTime >= :startDateTime and r.endDateTime <= :endDateTime and r.isCancelled = false")
    List<Reservation> getEntityReservation(Long entityId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select r from Reservation r where r.startDateTime >= :startDateTime and r.endDateTime <= :endDateTime and r.isCancelled = false")
    List<Reservation> getEntityReservation(LocalDateTime startDateTime, LocalDateTime endDateTime);

    Integer countAllByEntity(Entity entity);

    List<Reservation> findAllByEntityId(Long entityId);

    @Query("select r from Reservation r where r.entity.id = :entityId and (r.startDateTime <= current_timestamp and r.endDateTime >= current_timestamp or r.startDateTime > current_timestamp) and r.isCancelled = false")
    List<Reservation> findActiveAndFutureReservations(Long entityId);

    @Query("select r from Reservation r where r.endDateTime <= current_timestamp and r.isCancelled = false and r.loyaltyPointsAdded = false")
    List<Reservation> findFinishedReservations();
}
