import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { Reservation } from '../../../models/reservation.model';
import { ReservationService } from '../../../services/reservation.service';

@Component({
  selector: 'app-rate-experience-dialog',
  templateUrl: './rate-experience-dialog.component.html',
  styleUrls: ['./rate-experience-dialog.component.scss']
})
export class RateExperienceDialogComponent implements OnInit {

  rateForm!: FormGroup;
  reservation!: Reservation;
  starsSelected: number = 0;

  constructor(private dialogRef: MatDialogRef<RateExperienceDialogComponent>, private dialog: MatDialog, private reservationService: ReservationService, 
    @Inject(MAT_DIALOG_DATA) data: any) {
    this.reservation = data.reservation;
  }

  ngOnInit(): void {
    this.rateForm = new FormGroup({
      stars: new FormControl('', [Validators.required]),
      review: new FormControl('', [Validators.required])
    });
  }

  onSubmit(): void {
    if (this.rateForm.valid) {
      let review = {
        rating: this.rateForm.controls.stars?.value,
        comment: this.rateForm.controls.review?.value
      }
      this.reservationService.leaveReview(this.reservation.id, review).subscribe(
        _ => {
          this.reservation.status = "Completed";
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Feedback left",
            message: "You have succesfully left your feedback. Thank you for reviewing and helping our community get better.",
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        },
        _ => {
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Something went wrong",
            message: _.error.message,
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }
  
  isStarSelected(i: number){
    if(i <= this.starsSelected)
      return true;
    return false;
  }

  selectStar(i: number){
    this.starsSelected = i;
    this.rateForm.controls.stars?.setValue(i);
  }

}
