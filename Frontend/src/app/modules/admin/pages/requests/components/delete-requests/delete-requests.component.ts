import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DeleteRequestService } from 'src/app/modules/admin/services/delete-request.service';
import { DeleteRequestResponseDialogComponent } from '../delete-request-response-dialog/delete-request-response-dialog.component';

@Component({
  selector: 'app-delete-requests',
  templateUrl: './delete-requests.component.html',
  styleUrls: ['./delete-requests.component.scss']
})
export class DeleteRequestsComponent implements OnInit {
  requests!: any;

  constructor(private deleteRequestService: DeleteRequestService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.deleteRequestService.getAllRequests().subscribe(
      data => {
        this.requests = data
      }
    );
  }

  approveRequest(requestId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      requestId: requestId,
      approved: true
    }      
    this.dialog.open(DeleteRequestResponseDialogComponent, dialogConfig);
  }

  denyRequest(requestId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      requestId: requestId,
      approved: false
    }      
    this.dialog.open(DeleteRequestResponseDialogComponent, dialogConfig);
  }

}
