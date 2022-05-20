import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { ReservationHistory } from '../../../models/reservation-history.model';
import { ReservationService } from '../../../services/reservation.service';

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.scss']
})
export class ReportDialogComponent implements OnInit {

  reportSellerForm!: FormGroup;
  reservation!: ReservationHistory;

  constructor(private dialogRef: MatDialogRef<ReportDialogComponent>, private dialog: MatDialog, private reservationService: ReservationService, @Inject(MAT_DIALOG_DATA) data: any) {
    this.reservation = data.reservation;
  }

  ngOnInit(): void {
    this.reportSellerForm = new FormGroup({
      reportSellerMessage: new FormControl('', [Validators.required]),
    });
  }

  onSubmit(): void {
    if (this.reportSellerForm.valid) {
      this.reservationService.reportSeller(this.reportSellerForm.get("reportSellerMessage")?.value).subscribe(
        _ => {
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Seller reported",
            message: "You have reported this seller. Our administrators will look into this and will get back to you via email as soon as they can.",
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

}
