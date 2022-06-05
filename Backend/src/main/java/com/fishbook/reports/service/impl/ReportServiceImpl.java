package com.fishbook.reports.service.impl;

import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.exception.ReservationNotFinishedException;
import com.fishbook.reports.dao.ReportRepository;
import com.fishbook.reports.model.Report;
import com.fishbook.reports.service.ReportService;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final SellerReservationRepository reservationRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createReport(Report report, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
        if (!reservation.isFinished()) {
            throw new ReservationNotFinishedException();
        }

        report.setReservation(reservation);
        reportRepository.save(report);
    }
}
