import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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
  
  constructor(private registrationService: RegistrationService, private locationService: LocationService) { }

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
  }

  private initializeForm(): void{
    this.registrationForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      address : new FormControl('', [Validators.required]),
      city : new FormControl('', [Validators.required]),
      country : new FormControl('', [Validators.required]),
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
    else
      this.registrationService.postClient(this.registrationForm.value).subscribe();
  }

  onCountryChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.registrationForm.get('country')?.value);
    this.registrationForm.controls.city.setValue('');
  } 

}
