import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-options-dialog',
  templateUrl: './options-dialog.component.html',
  styleUrls: ['./options-dialog.component.scss']
})
export class OptionsDialogComponent {

  title: string = "";
  message: string = "";
  buttonYesText: string = "";
  buttonNoText: string = "";
  @Output() accept: EventEmitter<any> = new EventEmitter<any>();

  constructor(private dialogRef: MatDialogRef<OptionsDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any) {
    this.title = data.title;
    this.message = data.message;
    this.buttonYesText = data.buttonYesText;
    this.buttonNoText = data.buttonNoText;
  }

  handleAccept(): void{
    this.accept.emit();
    this.dialogRef.close();
  }


  close() {
    this.dialogRef.close();
  }

}
