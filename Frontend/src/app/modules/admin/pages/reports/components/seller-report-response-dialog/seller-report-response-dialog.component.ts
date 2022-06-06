import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SellerReport } from 'src/app/modules/admin/models/seller-report.model';
import { ReportService } from 'src/app/modules/admin/services/report.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';

@Component({
  selector: 'app-seller-report-response-dialog',
  templateUrl: './seller-report-response-dialog.component.html',
  styleUrls: ['./seller-report-response-dialog.component.scss']
})
export class SellerReportResponseDialogComponent implements OnInit {

  reportSellerForm!: FormGroup;
  sellerReport!: SellerReport;
  givePenalty: boolean = false;

  constructor(private dialogRef: MatDialogRef<SellerReportResponseDialogComponent>, private dialog: MatDialog, private reportService: ReportService, @Inject(MAT_DIALOG_DATA) data: any) {
    this.sellerReport = data.sellerReport;
  }

  ngOnInit(): void {
    this.reportSellerForm = new FormGroup({
      sellerMessage: new FormControl('', [Validators.required]),
      buyerMessage: new FormControl('', [Validators.required]),
    });
  }

  onSubmit(): void {
    if (this.reportSellerForm.valid) {
      let response = 
      {
        reportId: this.sellerReport.id,
        sellerMessage: this.reportSellerForm.get("sellerMessage")?.value,
        buyerMessage: this.reportSellerForm.get("buyerMessage")?.value,
        givePenalty: this.givePenalty,
        sellerEmail: this.sellerReport.sellerEmail,
        buyerEmail: this.sellerReport.buyerEmail,
        buyerArrived: this.sellerReport.buyerArrived
      }
      this.reportService.giveResponseOnSellerReport(response).subscribe(
        _ => {
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Response documented",
            message: "You have successfully left your response",
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
          window.location.reload();
        },
        _ => {
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Something went wrong",
            message: _.error.message,
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
          window.location.reload();
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }


}
