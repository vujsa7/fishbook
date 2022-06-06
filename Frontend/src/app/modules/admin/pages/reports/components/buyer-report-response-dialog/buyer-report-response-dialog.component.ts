import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { BuyerReportService } from 'src/app/modules/admin/services/buyer-report.service';
import { AvailabilityDialogComponent } from 'src/app/modules/seller/components/availability-dialog/availability-dialog.component';

@Component({
  selector: 'app-buyer-report-response-dialog',
  templateUrl: './buyer-report-response-dialog.component.html',
  styleUrls: ['./buyer-report-response-dialog.component.scss']
})
export class BuyerReportResponseDialogComponent implements OnInit {
  response: string = "";
  reportId: number = -1;

  constructor(private dialogRef: MatDialogRef<AvailabilityDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private toastr: ToastrService, private buyerReportService: BuyerReportService) {
    this.reportId = data.reportId;
  }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  sendResponse() {
    let response = {
      reportId: this.reportId, 
      response: this.response
    }
    this.buyerReportService.sendResponse(response).subscribe(
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
