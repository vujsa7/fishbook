import { DatePipe } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, NavigationEnd, Router, Event } from '@angular/router';
import { Subscription } from 'rxjs';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { OptionsDialogComponent } from 'src/app/shared/components/options-dialog/options-dialog.component';
import { RangeDatePickerComponent } from 'src/app/shared/components/range-date-picker/range-date-picker.component';
import { ReservationOfferDetails } from '../models/reservation-offer-details.model';
import { ReservationService } from '../services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {

  reservationForm!: FormGroup;
  routeSub!: Subscription;
  entityId!: number;
  reservationOfferDetails!: ReservationOfferDetails;
  range!: any;
  @ViewChild('rangeDatePicker') rangeDatePicker!: RangeDatePickerComponent;

  constructor(private route: ActivatedRoute, private reservationService: ReservationService, private dialog: MatDialog, private datePipe: DatePipe ) {
    this.reservationForm = new FormGroup({
      additionalServices: new FormArray([]),
    });
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.entityId = params['id'];
      this.fetchReservationOfferDetails();
    });
  }

  fetchReservationOfferDetails() {
    this.reservationOfferDetails = this.reservationService.getReservationOfferDetails(this.entityId);
    this.reservationOfferDetails.additionalServices.forEach(() => this.additionalServicesFormArray.push(new FormControl(false)));
  }

  get additionalServicesFormArray() {
    return this.reservationForm.controls.additionalServices as FormArray;
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  toggleAdditionalService(index: number){
    let formControl = this.additionalServicesFormArray.controls[index];
    let value = formControl.value;
    formControl.setValue(!value);
  }

  isDateRangeSelected(){
    if(this.range) return true;
    else return false;
  }

  onRangeChanged(range: any){
    this.range = range;
  }

  clearDateRange(){
    this.range = undefined;
    this.rangeDatePicker.clearDates();
  }

  getTotal(): number{
    let sum = 0;
    sum += this.sumAdditionalServices();
    if(this.range)
      sum += this.sumPriceForSelectedDates();
    return sum;
  }

  sumPriceForSelectedDates() {
    return this.reservationOfferDetails.pricePerDay * this.getDifferenceInDays(this.range.end, this.range.start);
  }
  
  getDifferenceInDays(date1: any, date2: any) {
    const diffInMs = Math.abs(date2 - date1);
    return diffInMs / (1000 * 60 * 60 * 24);
  }

  sumAdditionalServices() {
    let i = 0;
    let sum = 0;
    this.additionalServicesFormArray.controls.forEach(control => {
      if(control.value == true){
        sum += this.reservationOfferDetails.additionalServices[i].price;
      }
      i++;
    })
    return sum
  }

  openReservationDialog(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      title: "Make reservation?",
      message: "Are you sure you want to create reservation at " + this.reservationOfferDetails.entityName + " from " + this.datePipe.transform(this.range.start, 'longDate') + " to " + this.datePipe.transform(this.range.end, 'longDate') + " with the total of $" + this.getTotal() + "?",
      buttonNoText: "No",
      buttonYesText: "Yes"
    };
    const dialogRef = this.dialog.open(OptionsDialogComponent, dialogConfig);
    dialogRef.componentInstance.accept.subscribe(_ => {
      this.makeReservationCallBack();
    })
  }

  makeReservationCallBack(){
    this.reservationService.makeReservation().subscribe(
      data => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Reservation successfully made",
          message: "You have made a reservation.",
          buttonText: "Okay",
        };
        const dialogRef = this.dialog.open(InfoDialogComponent, dialogConfig);
        dialogRef.componentInstance.okay.subscribe(_ => {
          window.location.replace("/client/dashboard");
        })
      }
    );
  }

}
