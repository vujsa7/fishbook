package com.fishbook.reservation.service.impl;

import com.fishbook.email.model.Email;
import com.fishbook.email.service.EmailService;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.entity.model.Entity;
import com.fishbook.exception.EntityNotFoundException;
import com.fishbook.reservation.dao.ReservationReviewRepository;
import com.fishbook.reservation.model.ReservationReview;
import com.fishbook.reservation.service.ReservationReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationReviewServiceImpl implements ReservationReviewService {

    private final ReservationReviewRepository reservationReviewRepository;
    private final EntityRepository entityRepository;
    private final EmailService emailService;


    @Override
    public List<ReservationReview> getAll() {
        return reservationReviewRepository.findAll();
    }

    @Override
    @Transactional
    public void respond(Long reviewId, String response, Boolean approved) throws InterruptedException {
        ReservationReview review = reservationReviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
        Long entityId = review.getReservation().getEntity().getId();
        if (approved) {
            List<ReservationReview> allApprovedReviews = reservationReviewRepository.getAllApprovedReviews(entityId);
            Double rating = allApprovedReviews.stream()
                    .mapToDouble(ReservationReview::getRating)
                    .sum();
            rating = rating + review.getRating();
            Double count = ((double) allApprovedReviews.size() > 0.0 ? (double) allApprovedReviews.size() + 1.0 : 1.0);
            rating = rating / count;
            Entity ratedEntity = entityRepository.findById(entityId).orElseThrow(EntityNotFoundException::new);
            ratedEntity.setRating(rating);
            entityRepository.save(ratedEntity);
            review.setApproved(true);
            reservationReviewRepository.save(review);
            Email email = new Email("user.fishbook@gmail.com","Your service has been rated", "Rating from client " + review.getReservation().getClient().getEmail()
                    + ": " + review.getRating() + ". Current rating of your service: " + ratedEntity.getRating() + ". Response from admin: " + response);
            emailService.sendEmail(email);
        }
    }
}
