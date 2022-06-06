import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { DeleteRequestService } from 'src/app/modules/admin/services/delete-request.service';
import { AvailabilityDialogComponent } from 'src/app/modules/seller/components/availability-dialog/availability-dialog.component';

@Component({
  selector: 'app-delete-request-response-dialog',
  templateUrl: './delete-request-response-dialog.component.html',
  styleUrls: ['./delete-request-response-dialog.component.scss']
})
export class DeleteRequestResponseDialogComponent implements OnInit {
  header: string = "";
  response: string = "";
  requestId: number = -1;
  approved: boolean = false;

  constructor(private dialogRef: MatDialogRef<AvailabilityDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private toastr: ToastrService, private deleteRequestService: DeleteRequestService) { 
    data.approved ? this.header = "Approve request" : "Deny request";
    this.approved = data.approved;
    this.requestId = data.requestId;
  }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  sendResponse() {
    let response = {
      requestId: this.requestId, 
      response: this.response,
      approved: this.approved
    }
    this.deleteRequestService.sendResponse(response).subscribe(
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
