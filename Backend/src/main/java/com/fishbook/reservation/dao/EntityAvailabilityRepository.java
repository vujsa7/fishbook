package com.fishbook.reservation.dao;

import com.fishbook.reservation.model.EntityAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;

public interface EntityAvailabilityRepository extends JpaRepository<EntityAvailability, Long> {

    List<EntityAvailability> findAllByEntityId(Long entityId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    @Query("SELECT e FROM EntityAvailability e WHERE e.entity.id = :entityId")
    List<EntityAvailability> findEntityAvailabilities(@Param("entityId") Long entityId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
    @Query("SELECT e FROM EntityAvailability e WHERE e.entity.id = :entityId and (e.fromDateTime = :reservationEnd or e.toDateTime = :reservationStart)")
    List<EntityAvailability> findEntityAvailabilitiesToMerge(@Param("reservationStart") LocalDateTime reservationStart, @Param("reservationEnd") LocalDateTime reservationEnd, @Param("entityId") Long entityId);
}
