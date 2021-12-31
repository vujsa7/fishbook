import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RegistrationService } from 'src/app/shared/services/registration.service';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent implements OnInit {
  response: string = '';
  @Input() header: string = '';
  @Input() requestId: number = -1;
  @Output() notifyHideDialog: EventEmitter<any> = new EventEmitter<any>();
  @Output() notifyRemoveRequest: EventEmitter<any> = new EventEmitter<any>();
  
  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
  }

  hideDialog(){
    this.notifyHideDialog.emit();
  }

  sendResponse(){
    let approved = false;
    if(this.header == 'Approve request'){
      approved = true;
    }
    let response = {
      response: this.response,
      approved: approved
    };

    this.registrationService.deleteRegistrationRequest(this.requestId, response).subscribe();
    this.notifyHideDialog.emit();
    this.notifyRemoveRequest.emit();
  }
}
