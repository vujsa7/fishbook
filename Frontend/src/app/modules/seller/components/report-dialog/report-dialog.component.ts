import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ReportService } from '../../services/report.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.scss']
})
export class ReportDialogComponent implements OnInit {

  reportForm!: FormGroup;
  reservationId: number;
  reservation: any;

  constructor(private dialogRef: MatDialogRef<ReportDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private reservationService: ReservationService, private toastr: ToastrService, private reportService: ReportService) {
    this.reservationId = data.reservationId;
   }

  ngOnInit(): void {
    this.reservationService.getReservation(this.reservationId).subscribe(
      data => {
        this.reservation = data;
        this.initializeForm();
      }
    )
  }

  private initializeForm() {
    this.reportForm = new FormGroup({
      comment: new FormControl('', [Validators.required]),
      clientArrived: new FormControl(''),
      shouldGetPenalty: new FormControl('')
    })
  }

  close() {
    this.dialogRef.close();
  }

  saveReport() {
    let request = {
      reservationId: this.reservationId,
      comment: this.reportForm.controls.comment.value,
      clientArrived: this.reportForm.controls.clientArrived.value ? true : false,
      shouldGetPenalty: this.reportForm.controls.shouldGetPenalty.value ? true : false
    }
    this.reportService.postReport(request).subscribe(
      data => {
        this.toastr.success("Successfully saved reservation. Check your calendar.", "Success");
        location.reload();
        this.close();
      },
      error => { 
        this.toastr.error(error.error.message, "Error");
      }
    )
  }

}
