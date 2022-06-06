package com.fishbook.reports.service.impl;

import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
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
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final BuyerReportRepository buyerReportRepository;
    private final SellerReservationRepository sellerReservationRepository;
    private final ReservationRepository reservationRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

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

    @Override
    public List<Report> getSellerReports() {
        return reportRepository.findAll();
    }

    @Override
    public void createResponseOnSellerReport(Long reportId, String sellerMessage, String buyerMessage, Boolean givePenalty, String sellerEmail, String buyerEmail, Boolean buyerArrived) throws InterruptedException {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isEmpty())
            throw new ApiRequestException("No report with that id");
        Email emailToSeller = new Email("user.fishbook@gmail.com", "Response about your report", sellerMessage);
        Email emailToBuyer = new Email("user.fishbook@gmail.com", "A buyer reported you", buyerMessage);
        try {
            emailService.sendEmail(emailToSeller);
            emailService.sendEmail(emailToBuyer);
            User user = userRepository.findByEmail(buyerEmail);
            if (!buyerArrived) {
                user.setPenalties(user.getPenalties() + 1);
            }
            if (givePenalty) {
                user.setPenalties(user.getPenalties() + 1);
            }
            if (user.getPenalties() >= 3)
                user.setDeleted(true);
            userRepository.save(user);
            reportRepository.delete(reportOptional.get());
        } catch (InterruptedException e) {
            throw new InterruptedException("Emails not sent");
        }
    }

    @Override
    public List<BuyerReport> getAllBuyerReports() {
        return buyerReportRepository.findAll();
    }

    @Override
    public void respondToBuyerReport(Long id, String response) throws InterruptedException {
        BuyerReport report = buyerReportRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Email email = new Email("user.fishbook@gmail.com", "Response to report", "Response to report \"" + report.getComment() + "\" made by " + report.getReservation().getClient().getFirstName()
                + " " + report.getReservation().getClient().getLastName() + " for seller " + report.getReservation().getEntity().getOwner().getFirstName() + " " +
                report.getReservation().getEntity().getOwner().getLastName() + ":\n" + response);
        buyerReportRepository.delete(report);
        emailService.sendEmail(email);
        emailService.sendEmail(email);
    }
}

