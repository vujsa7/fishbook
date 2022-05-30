import { Component, Inject, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { AdditionalService } from 'src/app/shared/models/additional-service.model';
import { EntityService } from '../../services/entity.service';
import { SpecialOfferService } from '../../services/special-offer.service';

@Component({
  selector: 'app-special-offer-dialog',
  templateUrl: './special-offer-dialog.component.html',
  styleUrls: ['./special-offer-dialog.component.scss']
})
export class SpecialOfferDialogComponent implements OnInit {

  fromDate: any;
  toDate: any;
  validRange: boolean = false;
  specialOfferForm!: FormGroup;
  entityId: number;
  entityType: string = "";
  additionalServices: Array<AdditionalService> = new Array();
  minDate: Date = new Date();

  constructor(private dialogRef: MatDialogRef<SpecialOfferDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private entityService: EntityService, private specialOfferService: SpecialOfferService, private toastr: ToastrService) {
    this.entityId = data.entityId;
    this.entityType = data.entityType;
   }

  ngOnInit(): void {
    this.initializeForm();
    if (this.entityType == "boat") {
      this.entityService.fetchBoatDetails(this.entityId).subscribe(
        data => {
          this.additionalServices = data.additionalServices;
        }
      )
    }
    if (this.entityType == "adventure") {
      this.entityService.fetchAdventureDetails(this.entityId).subscribe(
        data => {
          this.additionalServices = data.additionalServices;
        }
      )
    }
    if (this.entityType == "house") {
      this.entityService.fetchHouseDetails(this.entityId).subscribe(
        data => {
          this.additionalServices = data.additionalServices;
        }
      )
    }
    this.minDate = new Date();
  }

  private initializeForm() {
    this.specialOfferForm = new FormGroup({
      maxNumberOfPeople: new FormControl('', [Validators.required]),
      discount: new FormControl('', [Validators.required]),
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
    const selectedAdditionalServices = (this.specialOfferForm.controls.additionalServices as FormArray);
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

  saveSpecialOffer() {
    let request = {
      startDateTime: this.fromDate,
      endDateTime: this.toDate,
      maxNumberOfPeople: this.specialOfferForm.controls.maxNumberOfPeople.value,
      entityId: this.entityId,
      discount: this.specialOfferForm.controls.discount.value,
      additionalServices: this.specialOfferForm.controls.additionalServices.value
    }
    this.specialOfferService.postSpecialOffer(request).subscribe(
      data => {
        this.toastr.success("Successfully saved special offer. Check your calendar.", "Success");
        this.close();
      },
      error => { 
        this.toastr.error(error.error.message, "Error");
      }
    )
  }

}
