import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { LocationService } from '../../services/location.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  personalDetailsForm!: FormGroup;
  securityDetailsForm!: FormGroup;
  countries: Country[] = [];
  cities: City[] = [];
  filteredCities: City[] = [];
  user: any = {};

  constructor(private locationService: LocationService, private router: Router, private userService: UserService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.initializeForms();
    this.locationService.getCountries().subscribe(
      data => {
        this.countries = data;
      }
    );
    this.locationService.getCities().subscribe(
      data => {
        this.cities = data;
        this.filteredCities = data;
      }
    );
    this.userService.getUser().subscribe(
      data => {
        this.user = data;
        this.insertUserData();
      },
      error => {
        this.router.navigate(['/login']);
      }
    )
  }

  onCountryChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.personalDetailsForm.get('country')?.value);
    this.personalDetailsForm.controls.city.setValue('');
  }

  private initializeForms(): void{
    this.initializePersonalDetailsForm();
    this.initializeSecurityDetailsForm();
  }

  private initializePersonalDetailsForm() {
    this.personalDetailsForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),
    });
  }

  private initializeSecurityDetailsForm() {
    this.securityDetailsForm = new FormGroup({
      email: new FormControl(''),
      currentPassword: new FormControl('', [Validators.required]),
      passwords: new FormGroup({
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
      }, [this.passwordMatchValidator])
    });
    this.securityDetailsForm.controls['email'].disable();
  }

  insertUserData() : void {
    this.personalDetailsForm.get('firstName')?.setValue(this.user.firstName);
    this.personalDetailsForm.get('lastName')?.setValue(this.user.lastName);
    this.personalDetailsForm.get('address')?.setValue(this.user.address);
    this.personalDetailsForm.get('country')?.setValue(this.user.country);
    this.personalDetailsForm.get('city')?.setValue(this.user.city);
    this.personalDetailsForm.get('phoneNumber')?.setValue(this.user.phoneNumber);
    this.securityDetailsForm.get('email')?.setValue(this.user.email);
  }

  passwordMatchValidator(c: AbstractControl) {
    if (c.get('password')?.value != c.get('confirmPassword')?.value) {
      return {noMatch: true};
    }
    return null;
  }

  getPasswordsControl(){
    return (this.securityDetailsForm.get('passwords') as FormGroup).controls;
  }

  confirmPasswordErrorMessage() : string {
    if(this.getPasswordsControl().confirmPassword.touched){
      if (this.getPasswordsControl().confirmPassword.hasError('required')) {
        return 'You must confirm your password';
      } else if (this.securityDetailsForm.get('passwords')?.hasError('noMatch')) {
        return 'Passwords do not match';
      } 
    }
    return "";
  }

  updateProfile() : void {
    let user = {
      firstName: this.personalDetailsForm.get("firstName")?.value,
      lastName: this.personalDetailsForm.get("lastName")?.value,
      email: this.personalDetailsForm.get("email")?.value,
      address: {
        address: this.personalDetailsForm.get("address")?.value,
        city: this.cities.filter(c => c.name == this.personalDetailsForm.get("city")?.value)[0]
      },
      phoneNumber: this.personalDetailsForm.get("phoneNumber")?.value
    };
    this.userService.updateUser(user).subscribe(
      data => {
        this.toastr.success("Personal details successfully updated.", "Success");
      },
      error => {
        this.toastr.error(error.message, "Error");
      })
  }

  changePassword() : void {
    let passwordUpateRequest = {
      currentPassword: this.securityDetailsForm.get("currentPassword")?.value,
      newPassword: this.getPasswordsControl().confirmPassword?.value
    };
    this.userService.updatePassword(passwordUpateRequest).subscribe(
      data => {
        this.toastr.success("Security details successfully updated.", "Success");
        this.initializeSecurityDetailsForm();
        this.securityDetailsForm.get('email')?.setValue(this.user.email);
      },
      error => {
        this.toastr.error(error.message, "Error");
      }
    )
  }

}
