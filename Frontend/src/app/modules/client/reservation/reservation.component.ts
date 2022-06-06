import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import _ from 'lodash';
import { Subscription } from 'rxjs';
import DateUtil from 'src/app/core/utils/date.util';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { OptionsDialogComponent } from 'src/app/shared/components/options-dialog/options-dialog.component';
import { RangeDatePickerComponent } from 'src/app/shared/components/range-date-picker/range-date-picker.component';
import { AdditionalService } from 'src/app/shared/models/additional-service.model';
import { DateRange } from 'src/app/shared/models/date-range.model';
import { ReservationOfferDetails } from '../models/reservation-offer-details.model';
import { SpecialOfferDetails } from '../models/special-offer-details.model';
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
  boatOwnerUnavailableDates!: Array<DateRange>;
  unavailableDatesForSelection: Array<DateRange> = [];
  isSkipperSelected: boolean = false;
  discountAndFees!: any;
  specialOfferId: any;
  specialOfferDetails!: SpecialOfferDetails;


  constructor(private route: ActivatedRoute, private reservationService: ReservationService, private dialog: MatDialog, private datePipe: DatePipe, private router: Router) {
    this.reservationForm = new FormGroup({
      additionalServices: new FormArray([]),
    });
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      if (this.router.url.includes("reservation")) {
        this.entityId = params['id'];
        this.fetchReservationOfferDetails();
        this.fetchDiscountsAndFees(this.entityId);
      } else if (this.router.url.includes("specialOffers")) {
        this.specialOfferId = params['id'];
        this.fetchSpecialOfferDetails();
        
      }

    });
  }


  fetchSpecialOfferDetails() {
    this.reservationService.getSpecialOfferDetails(this.specialOfferId).subscribe(
      data => {
        this.specialOfferDetails = data;
        this.fetchDiscountsAndFees(data.entityId);
      }
    )
  }

  fetchReservationOfferDetails() {
    this.reservationService.getReservationOfferDetails(this.entityId).subscribe(data => {
      this.reservationOfferDetails = data;
      this.reservationOfferDetails.additionalServices.forEach(() => this.additionalServicesFormArray.push(new FormControl(false)));
      if (_.includes(_.map(this.reservationOfferDetails.additionalServices, _.property('name')), "Skipper")) {
        this.reservationService.getBoatOwnerUnavailability(this.entityId).subscribe(
          data => {
            this.boatOwnerUnavailableDates = data;
          }
        );
      }
    });
  }

  fetchDiscountsAndFees(entityId: number) {
    this.reservationService.fetchDiscountAndFees(entityId).subscribe(data => {
      this.discountAndFees = data;
    })
  }

  get additionalServicesFormArray() {
    return this.reservationForm.controls.additionalServices as FormArray;
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  toggleAdditionalService(index: number) {
    let formControl = this.additionalServicesFormArray.controls[index];
    let value = formControl.value;
    formControl.setValue(!value);
    if (this.reservationOfferDetails.additionalServices[index].name == "Skipper") {
      this.isSkipperSelected = formControl.value;
      this.updateAvailableDateRanges();
    }
  }

  updateAvailableDateRanges() {
    if (this.isSkipperSelected && this.boatOwnerUnavailableDates && this.boatOwnerUnavailableDates.length != 0) {
      if (this.range && this.range.start && this.range.end && this.isSelectedDateRangeUnavailable()) {
        this.clearDateRange();
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Skipper is not available",
          message: "For selected time the skipper is not available. Please choose another date from calendar.",
          buttonText: "Got it"
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      }
      this.unavailableDatesForSelection = _.cloneDeep(this.boatOwnerUnavailableDates);
    } else {
      this.unavailableDatesForSelection = [];
    }
  }

  isSelectedDateRangeUnavailable(): boolean {
    for (let unavailableDateRange of this.boatOwnerUnavailableDates) {
      if (DateUtil.isOverlapping(unavailableDateRange, this.range))
        return true;
    }
    return false;
  }

  isDateRangeSelected() {
    if (this.range) return true;
    else return false;
  }

  onRangeChanged(range: any) {
    this.range = range;
  }

  clearDateRange() {
    this.range = null;
    this.rangeDatePicker.clearDates();
  }

  getTotal(): number {
    let sum = 0;
    sum += this.sumAdditionalServices();
    if (this.range)
      sum += this.sumPriceForSelectedDates();
    if (this.discountAndFees) {
      if (this.discountAndFees.discount > 0)
        sum -= sum * this.discountAndFees.discount / 100;
      if (this.discountAndFees.sellerFee > 0)
        sum += sum * this.discountAndFees.sellerFee / 100;
      if (this.discountAndFees.systemFee > 0)
        sum += this.discountAndFees.systemFee;
    }
    return sum;
  }

  getSpecialOfferTotal(): number {
    let sum = this.specialOfferDetails.price;
    if (this.discountAndFees) {
      if (this.discountAndFees.discount > 0)
        sum -= sum * this.discountAndFees.discount / 100;
      if (this.discountAndFees.sellerFee > 0)
        sum += sum * this.discountAndFees.sellerFee / 100;
      if (this.discountAndFees.systemFee > 0)
        sum += this.discountAndFees.systemFee;
    }
    return sum;
  }

  sumPriceForSelectedDates() {
    return this.reservationOfferDetails.pricePerDay * DateUtil.getDifferenceInDays(this.range.end, this.range.start);
  }

  getDifferenceInDays(date1: any, date2: any) {
    return DateUtil.getDifferenceInDays(date1, date2);
  }

  sumAdditionalServices() {
    let i = 0;
    let sum = 0;
    this.additionalServicesFormArray.controls.forEach(control => {
      if (control.value == true) {
        sum += this.reservationOfferDetails.additionalServices[i].price;
      }
      i++;
    })
    return sum
  }

  openReservationDialog() {
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

  makeReservationCallBack() {
    this.range.start.setMinutes(this.range.start.getMinutes() - this.range.start.getTimezoneOffset());
    this.range.end.setMinutes(this.range.end.getMinutes() - this.range.end.getTimezoneOffset());
    let additionalServices = new Array<AdditionalService>();
    _.forEach(this.reservationForm.get("additionalServices")?.value, (additionalService, i: number) => {
      if (additionalService) {
        additionalServices.push(this.reservationOfferDetails.additionalServices[i]);
      }
    })
    let reservation = {
      start: this.range.start,
      end: this.range.end,
      totalPrice: this.getTotal(),
      additionalServices: additionalServices,
      entityId: this.entityId
    }
    this.reservationService.makeReservation(reservation).subscribe(
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
      },
      error => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Reservation failed",
          message: error.error.message,
          buttonText: "Okay",
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      }
    );
  }

  openSpecialOfferDialog(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      title: "Make reservation?",
      message: "Are you sure you want to create reservation at " + this.specialOfferDetails.entityName + " from " + this.datePipe.transform(this.specialOfferDetails.start, 'longDate') + " to " + this.datePipe.transform(this.specialOfferDetails.end, 'longDate') + " with the total of $" + this.getSpecialOfferTotal() + "?",
      buttonNoText: "No",
      buttonYesText: "Yes"
    };
    const dialogRef = this.dialog.open(OptionsDialogComponent, dialogConfig);
    dialogRef.componentInstance.accept.subscribe(_ => {
      this.makeReservationOnSpecialOfferCallBack();
    })
  }

  makeReservationOnSpecialOfferCallBack() {
    this.reservationService.makeReservationOnSpecialOffer(this.specialOfferId).subscribe(
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
      },
      error => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Reservation failed",
          message: error.error.message,
          buttonText: "Okay",
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      }
    )
  }

}
