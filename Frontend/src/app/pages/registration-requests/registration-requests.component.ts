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

  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
    this.registrationService.getRegistartionRequests().subscribe(
      data => {
        this.requests = data;
      }
    );
  }

}
