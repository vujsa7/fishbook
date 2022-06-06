import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BuyerReportService } from 'src/app/modules/admin/services/buyer-report.service';
import { BuyerReportResponseDialogComponent } from '../buyer-report-response-dialog/buyer-report-response-dialog.component';

@Component({
  selector: 'app-buyer-reports',
  templateUrl: './buyer-reports.component.html',
  styleUrls: ['./buyer-reports.component.scss']
})
export class BuyerReportsComponent implements OnInit {
  reports!: any;

  constructor(private buyerReportsService: BuyerReportService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.buyerReportsService.getAllReports().subscribe(
      data => {
        this.reports = data
      }
    );
  }

  respond(reportId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reportId: reportId
    }      
    this.dialog.open(BuyerReportResponseDialogComponent, dialogConfig);
  }

}
