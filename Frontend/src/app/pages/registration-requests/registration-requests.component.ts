import { Component, OnInit } from '@angular/core';
import { RegistrationRequest } from 'src/app/models/registration/registration-request.model';
import { RegistrationService } from 'src/app/shared/services/registration.service';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.scss']
})
export class RegistrationRequestsComponent implements OnInit {
  requests: RegistrationRequest[] = [];
  dialogVisible: boolean = false;
  header: string = 'Whatever';
  requestId: number = -1;

  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
    this.registrationService.getRegistartionRequests().subscribe(
      data => {
        this.requests = data;
      }
    );
  }

  showDialog(header: string, requestId: number): void {
    this.dialogVisible = true;
    this.header = header;
    this.requestId = requestId;
  }

  hideDialog(): void {
    this.dialogVisible = false;
  }

  removeRequest(): void {
    this.requests = this.requests.filter(request => request.id != this.requestId);
  }

}
