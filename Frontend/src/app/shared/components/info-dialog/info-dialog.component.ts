import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss']
})
export class InfoDialogComponent {

  title: string = "";
  message: string = "";
  buttonText: string = "";

  constructor(private dialogRef: MatDialogRef<InfoDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any) {
    this.title = data.title;
    this.message = data.message;
    this.buttonText = data.buttonText;
  }

  close() {
    this.dialogRef.close();
  }

}
