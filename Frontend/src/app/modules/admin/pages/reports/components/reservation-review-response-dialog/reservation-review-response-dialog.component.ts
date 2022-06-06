import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ReservationReviewService } from 'src/app/modules/admin/services/reservation-review.service';
import { AvailabilityDialogComponent } from 'src/app/modules/seller/components/availability-dialog/availability-dialog.component';

@Component({
  selector: 'app-reservation-review-response-dialog',
  templateUrl: './reservation-review-response-dialog.component.html',
  styleUrls: ['./reservation-review-response-dialog.component.scss']
})
export class ReservationReviewResponseDialogComponent implements OnInit {
  header: string = "Respond to review";
  response: string = "";
  reviewId: number = -1;
  approved: boolean = false;

  constructor(private dialogRef: MatDialogRef<AvailabilityDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private toastr: ToastrService, private reviewService: ReservationReviewService) { 
    this.approved = data.approved;
    this.reviewId = data.reviewId;
  }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  sendResponse() {
    let response = {
      reservationReviewId: this.reviewId, 
      response: this.response,
      approved: this.approved
    }
    this.reviewService.sendResponse(response).subscribe(
      data => {
        this.toastr.success("Success");
        location.reload();
        this.close();
      }, 
      error => {
        this.toastr.error(error.error.message);
      }
    )
  }

}
