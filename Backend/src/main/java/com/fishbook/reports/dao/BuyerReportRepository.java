package com.fishbook.reports.dao;

import com.fishbook.reports.model.BuyerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerReportRepository extends JpaRepository<BuyerReport, Long> {

    Optional<BuyerReport> findOneByReservationId(Long reservationId);
}
