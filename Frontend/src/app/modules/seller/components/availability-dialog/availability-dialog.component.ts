import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { EntityAvailabilityService } from '../../services/entity-availability.service';
import { SellerAvailabilityService } from '../../services/seller-availability.service';

@Component({
  selector: 'app-availability-dialog',
  templateUrl: './availability-dialog.component.html',
  styleUrls: ['./availability-dialog.component.scss']
})
export class AvailabilityDialogComponent implements OnInit {

  entityType: string = "";
  entityId: number = -1;
  fromDate: any;
  toDate: any;
  validRange: boolean = false;

  constructor(private sellerAvailabilityService: SellerAvailabilityService, private entityAvailabilityService: EntityAvailabilityService, private dialogRef: MatDialogRef<AvailabilityDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private toastr: ToastrService) {
    this.entityType = data.entityType;
    this.entityId = data.entityId;
   }

  ngOnInit(): void {
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

  setAvailability() {
    if(this.entityType == "adventure") {
      this.setSellerAvailability();
    } else {
      this.setEntityAvailability();
    }
  }

  setSellerAvailability() {
    let request = {
      fromDateTime: this.fromDate,
      toDateTime: this.toDate
    }
    this.sellerAvailabilityService.postSellerAvailability(request).subscribe(
      data => {
      this.toastr.success("Successfully set your availability. Check your calendar.", "Success");
      this.close();
      },
      error => { 
        this.toastr.error(error.error.message, "Error");
      })
  }

  setEntityAvailability() {
    let request = {
      fromDateTime: this.fromDate,
      toDateTime: this.toDate,
      entityId: this.entityId
    }
    this.entityAvailabilityService.postEntityAvailability(request).subscribe(
      data => {
      this.toastr.success("Successfully set your availability. Check your calendar.", "Success");
      this.close();
      },
      error => { 
        this.toastr.error(error.error.message, "Error");
      })
  }

}
