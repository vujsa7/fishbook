import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { LocationService } from 'src/app/shared/services/location.service';
import { RegistrationService } from 'src/app/shared/services/registration.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationForSeller: boolean = false;
  registrationForm!: FormGroup;
  countries: Country[] = [];
  cities: City[] = [];
  filteredCities: City[] = [];
  messageDialogTitle: string = "";
  messageDialogMessage: string = "";
  messageDialogButtonText: string = "";
  isMessageDialogVisible: boolean = false;
  
  constructor(private registrationService: RegistrationService, private locationService: LocationService, private route: Router) { }

  ngOnInit(): void {
    this.initializeForm();
    this.locationService.getCountries().subscribe(
      data => {
        this.countries = data;
      }
    )
    this.locationService.getCities().subscribe(
      data => {
        this.cities = data;
        this.filteredCities = data;
      }
    )
    console.log((this.registrationForm.get('passwords') as FormGroup).controls.password);
  }

  private initializeForm(): void{
    this.registrationForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      address : new FormControl('', [Validators.required]),
      city : new FormControl('', [Validators.required]),
      country : new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', [Validators.required]),
      passwords: new FormGroup({
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
      }, [this.passwordMatchValidator]),
      registrationType: new FormControl({value: '', disabled: true}, [Validators.required]),
      registrationMessage: new FormControl({value: '', disabled: true}, [Validators.required])
    });
  }

  confirmPasswordErrorMessage() : string {
    if(this.getPasswordsControl().confirmPassword.touched){
      if (this.getPasswordsControl().confirmPassword.hasError('required')) {
        return 'You must confirm your password';
      } else if (this.registrationForm.get('passwords')?.hasError('noMatch')) {
        return 'Passwords do not match';
      } 
    }
    return "";
  }

  passwordMatchValidator(c: AbstractControl) {
    if (c.get('password')?.value != c.get('confirmPassword')?.value) {
      return {noMatch: true};
    }
    return null;
  }

  getPasswordsControl(){
    return (this.registrationForm.get('passwords') as FormGroup).controls;
  }

  registrationTypeChanged() : void{
    if(this.registrationForSeller){
      this.registrationForm.get("registrationType")?.enable();
      this.registrationForm.get("registrationMessage")?.enable();
    } else {
      this.registrationForm.get("registrationType")?.disable();
      this.registrationForm.get("registrationMessage")?.disable();
    }
      
  } 

  onSubmit() : void{
    if(this.registrationForm.valid){
      if(this.registrationForSeller){
        const seller = {
          firstName: this.registrationForm.get("firstName")?.value,
          lastName: this.registrationForm.get("lastName")?.value,
          email: this.registrationForm.get("email")?.value,
          address: this.registrationForm.get("address")?.value,
          city: this.registrationForm.get("city")?.value,
          country: this.registrationForm.get("country")?.value,
          phoneNumber: this.registrationForm.get("phoneNumber")?.value,
          password: this.getPasswordsControl().confirmPassword?.value,
          registrationType: this.registrationForm.get("registrationType")?.value,
          registrationMessage: this.registrationForm.get("registrationMessage")?.value
        };
        this.registrationService.postRegistrationRequest(seller).subscribe(
          data => {
            this.showMessageDialog("Waiting for admin approval", "Admin must first approve your request. Once your account is approved you will be norified by email.", "Okay");
          },
          error => {
            // Implement error
          }
        );
      }
      else{
        const user = {
          firstName: this.registrationForm.get("firstName")?.value,
          lastName: this.registrationForm.get("lastName")?.value,
          email: this.registrationForm.get("email")?.value,
          address: this.registrationForm.get("address")?.value,
          city: this.registrationForm.get("city")?.value,
          phoneNumber: this.registrationForm.get("phoneNumber")?.value,
          password: this.getPasswordsControl().confirmPassword?.value
        };
        this.registrationService.postClient(user).subscribe(
          data => {
            this.showMessageDialog("Check your email", "Thanks for signing up. To active your account, please verify your email address by checking your email inbox.", "Okay");
          },
          error => {
            // Implement error
          }
        );
      }
    }
    
  }

  onCountryChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.registrationForm.get('country')?.value);
    this.registrationForm.controls.city.setValue('');
  }

  

  showMessageDialog(title: string, message: string, buttonText: string) {
    this.messageDialogTitle = title;
    this.messageDialogMessage = message;
    this.messageDialogButtonText = buttonText;
    this.isMessageDialogVisible = true;
  }

  onMessageDialogNotify(message: string): void{
    if(message == "close"){
      this.isMessageDialogVisible = false;
      this.route.navigate(["/homepage"]);
    }
      
  }

}
