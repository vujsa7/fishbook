import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/modules/admin/services/user.service';
import { AdditionalService } from 'src/app/shared/models/additional-service.model';
import { EntityService } from '../../services/entity.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-reservation-dialog',
  templateUrl: './reservation-dialog.component.html',
  styleUrls: ['./reservation-dialog.component.scss']
})
export class ReservationDialogComponent implements OnInit {

  fromDate: any;
  toDate: any;
  validRange: boolean = false;
  reservationForm!: FormGroup;
  entityId: number;
  entityType: string = "";
  additionalServices: Array<AdditionalService> = new Array();
  minDate: Date = new Date();
  users: any[] = [];

  constructor(private dialogRef: MatDialogRef<ReservationDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private entityService: EntityService, private userService: UserService, private toastr: ToastrService, private reservationService: ReservationService) {
    this.entityId = data.entityId;
    this.entityType = data.entityType;
   }

   ngOnInit(): void {
    this.initializeForm();
    if (this.entityType == "boat") {
      this.entityService.fetchBoatDetails(this.entityId).subscribe(
        data => {
          this.additionalServices = data.additionalServices;
          this.getUsers();
        }
      )
    }
    if (this.entityType == "adventure") {
      this.entityService.fetchAdventureDetails(this.entityId).subscribe(
        data => {
          this.additionalServices = data.additionalServices;
          this.getUsers();
        }
      )
    }
    if (this.entityType == "house") {
      this.entityService.fetchHouseDetails(this.entityId).subscribe(
        data => {
          this.additionalServices = data.additionalServices;
          this.getUsers();
        }
      )
    }
    this.minDate = new Date();
  }

  private getUsers() : void {
    this.userService.getUsersByRole('ROLE_CLIENT').subscribe(
      data => {
        this.users = data;
      }
    );
  }

  private initializeForm() {
    this.reservationForm = new FormGroup({
      maxNumberOfPeople: new FormControl('', [Validators.required]),
      clientId: new FormControl('', [Validators.required]),
      additionalServices: new FormArray([])
    })
  }

  onFromDateChanged(event: any) {
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

  additionalServicesChanged(event: any, value: AdditionalService) {
    const selectedAdditionalServices = (this.reservationForm.controls.additionalServices as FormArray);
    if (event.target.checked) {
      selectedAdditionalServices.push(new FormControl(value));
    } else {
      const index = selectedAdditionalServices.controls
        .findIndex(x => x.value === value);
      selectedAdditionalServices.removeAt(index);
    }
  }

  close() {
    this.dialogRef.close();
  }

  saveReservation() {
    let request = {
      startDateTime: this.fromDate,
      endDateTime: this.toDate,
      maxNumberOfPeople: this.reservationForm.controls.maxNumberOfPeople.value,
      entityId: this.entityId,
      clientId: this.reservationForm.controls.clientId.value,
      additionalServices: this.reservationForm.controls.additionalServices.value
    }
    this.reservationService.postReservation(request).subscribe(
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
