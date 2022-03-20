import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { AdminService } from 'src/app/modules/admin/services/admin.service';
import { LocationService } from 'src/app/shared/services/location.service';

@Component({
  selector: 'app-admin-form',
  templateUrl: './admin-form.component.html',
  styleUrls: ['./admin-form.component.scss']
})
export class AdminFormComponent implements OnInit {
  registrationForm!: FormGroup;
  countries: Country[] = [];
  cities: City[] = [];
  filteredCities: City[] = [];

  constructor(private adminService: AdminService, private locationService: LocationService, private route: Router) { }

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
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }

  onCountryChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.registrationForm.get('country')?.value);
    this.registrationForm.controls.city.setValue('');
  }

  onSubmit(): void {
    const newAdmin = {
      firstName: this.registrationForm.get("firstName")?.value,
      lastName: this.registrationForm.get("lastName")?.value,
      email: this.registrationForm.get("email")?.value,
      address: {
        address: this.registrationForm.get("address")?.value,
        city: this.cities.filter(c => c.name == this.registrationForm.get("city")?.value)[0]
      },
      phoneNumber: this.registrationForm.get("phoneNumber")?.value,
      password: this.registrationForm.get("password")?.value
    };
    this.adminService.postAdmin(newAdmin).subscribe(
      data => {
        this.registrationForm.reset();
      },
      error => {
        if(error.status == 422){
          alert("Email already exists");
        } else {
          alert(error.message);
        }
      }
    );
  }

}
