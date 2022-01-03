import { Component, OnInit } from '@angular/core';
import { RegistrationRequest } from 'src/app/models/registration/registration-request.model';
import { RegistrationService } from 'src/app/shared/services/registration.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationRequest: RegistrationRequest = new RegistrationRequest(-1, "", "", "", "", "", "", "", "", "", "");
  registrationExpanded: boolean = false;

  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
  }

  submitRequest(){
    this.registrationService.postRegistrationRequest(this.registrationRequest).subscribe();
  }

}
