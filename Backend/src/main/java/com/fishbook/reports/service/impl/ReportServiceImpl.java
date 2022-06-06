package com.fishbook.reports.service.impl;

import com.fishbook.exception.ApiRequestException;
import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.exception.ReservationNotFinishedException;
import com.fishbook.reports.dao.BuyerReportRepository;
import com.fishbook.reports.dao.ReportRepository;
import com.fishbook.reports.model.BuyerReport;
import com.fishbook.reports.model.Report;
import com.fishbook.reports.service.ReportService;
import com.fishbook.reservation.dao.ReservationRepository;
import com.fishbook.reservation.dao.SellerReservationRepository;
import com.fishbook.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final BuyerReportRepository buyerReportRepository;
    private final SellerReservationRepository sellerReservationRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createReport(Report report, Long reservationId) {
        Reservation reservation = sellerReservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
        if (!reservation.isFinished()) {
            throw new ReservationNotFinishedException();
        }

        report.setReservation(reservation);
        reportRepository.save(report);
    }

    @Override
    public void createBuyerReport(String message, Long reservationId, String email) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if(reservationOptional.isEmpty())
            throw new ApiRequestException("There is no reservation with that id.");
        Reservation reservation = reservationOptional.get();
        if(!reservation.getClient().getEmail().equals(email))
            throw new ApiRequestException("Bad request!");
        LocalDateTime now = LocalDateTime.now();
        now = now.with(LocalTime.MIDNIGHT);
        if(reservation.getEndDateTime().isAfter(now))
            throw new ApiRequestException("Reservation is not yet finished, you can't report.");
        Optional<BuyerReport> reportOptional = buyerReportRepository.findOneByReservationId(reservationId);
        if(reportOptional.isPresent())
            throw new ApiRequestException("You have already reported this seller on this occasion. Please wait for the administrator's response on your email.");
        BuyerReport report = BuyerReport.builder().comment(message).reservation(reservation).build();
        buyerReportRepository.save(report);
    }
}
