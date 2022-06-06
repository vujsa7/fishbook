import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SellerReport } from '../../models/seller-report.model';
import { ReportService } from '../../services/report.service';
import { SellerReportResponseDialogComponent } from './components/seller-report-response-dialog/seller-report-response-dialog.component';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  selectedButton: string = 'seller-reports';
  sellerReports!: Array<SellerReport>;

  constructor(private reportService: ReportService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.reportService.getSellerReports().subscribe(data => {
      this.sellerReports = data;
    })
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

  openResponseDialog(sellerReport: SellerReport) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      sellerReport: sellerReport
    };
    this.dialog.open(SellerReportResponseDialogComponent, dialogConfig);
  }

}
