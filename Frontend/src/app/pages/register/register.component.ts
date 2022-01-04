import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from 'src/app/shared/services/registration.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  //registrationRequest: RegistrationRequest = new RegistrationRequest();
  //registrationRequest: RegistrationRequest = new RegistrationRequest();
  registrationForSeller: boolean = false;
  registrationForm!: FormGroup;
  
  constructor(private registrationService: RegistrationService) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void{
    this.registrationForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      address : new FormControl('', [Validators.required]),
      city : new FormControl(''),
      country : new FormControl(''),
      email: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
      registrationType: new FormControl({value: '', disabled: true}, [Validators.required]),
      registrationMessage: new FormControl({value: '', disabled: true}, [Validators.required])
    });
  }

  registrationTypeChanged() : void{
    if(this.registrationForSeller){
      this.registrationForm.get("registrationType")?.enable();
      this.registrationForm.get("registrationMessage")?.enable();
    }
    else {
      this.registrationForm.get("registrationType")?.disable();
      this.registrationForm.get("registrationMessage")?.disable();
    }
      
  } 

  onSubmit() : void{
    console.log(this.registrationForm.value)
    if(this.registrationForSeller)
      this.registrationService.postRegistrationRequest(this.registrationForm.value).subscribe();
    // else
    //   this.registrationService.postClient(this.registrationRequest).subscribe();
  }

}
