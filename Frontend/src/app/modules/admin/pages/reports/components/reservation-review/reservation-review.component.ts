import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ReservationReviewService } from 'src/app/modules/admin/services/reservation-review.service';
import { ReservationReviewResponseDialogComponent } from '../reservation-review-response-dialog/reservation-review-response-dialog.component';

@Component({
  selector: 'app-reservation-review',
  templateUrl: './reservation-review.component.html',
  styleUrls: ['./reservation-review.component.scss']
})
export class ReservationReviewComponent implements OnInit {
  reviews!: any;

  constructor(private reviewService: ReservationReviewService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.reviewService.getAll().subscribe(
      data => {
        this.reviews = data;
        this.reviews = this.reviews.filter((r: { approved: boolean; }) => r.approved != true && r.approved != false);
      }
    );
  }

  approveReview(reviewId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reviewId: reviewId,
      approved: true
    }      
    this.dialog.open(ReservationReviewResponseDialogComponent, dialogConfig);
  }

  denyReview(reviewId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reviewId: reviewId,
      approved: false
    }      
    this.dialog.open(ReservationReviewResponseDialogComponent, dialogConfig);
  }

}
