import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-calculate-revenue-dialog',
  templateUrl: './calculate-revenue-dialog.component.html',
  styleUrls: ['./calculate-revenue-dialog.component.scss']
})
export class CalculateRevenueDialogComponent implements OnInit {

  entityId: number = -1;
  fromDate: any;
  toDate: any;
  validRange: boolean = false;
  minDate: Date = new Date();
  revenueForm!: FormGroup;

  constructor(private dialogRef: MatDialogRef<CalculateRevenueDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private toastr: ToastrService, private reservationService: ReservationService) {
    this.entityId = data.entityId;
   }

  ngOnInit(): void {
    this.initializeForm();
    this.minDate = new Date();
  }
  
  private initializeForm() {
    this.revenueForm = new FormGroup({
      revenue: new FormControl({value: 0, disabled: true}, Validators.required)
    })
  }

  onFromDateChanged(event: any){
    this.fromDate = event.target.value;
    if(this.fromDate < this.toDate) {
      this.validRange = true;
    } else {
      this.validRange = false;
    }
  }

  onToDateChanged(event: any) {
    this.toDate = event.target.value;
    if(this.fromDate < this.toDate) {
      this.validRange = true;
    } else {
      this.validRange = false;
    }
  }

  close() {
    this.dialogRef.close();
  }

  calculateRevenue() {
    let request = {
      startDate: this.fromDate,
      endDate: this.toDate,
      entityId: this.entityId
    }
    this.reservationService.calculateRevenue(request).subscribe(
      data => {
        this.revenueForm.controls.revenue.setValue(data);
        this.toastr.success("Successfully calculated your revenue", "Success");
      },
      error => { 
        this.toastr.error(error.error.message, "Error");
      }
    )
  }

}
