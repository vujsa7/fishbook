import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { LocationService } from '../../services/location.service';
import { UserService } from '../../services/user.service';
import { DeleteAccountDialogComponent } from '../delete-account-dialog/delete-account-dialog.component';
import { InfoDialogComponent } from '../info-dialog/info-dialog.component';

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
  newProfileImg: any;
  userProfileImg!: string;
  profileType: string = "";
  levelMarks: Array<number> = [];
  points: number = -1;
  

  constructor(private authService: AuthService, private locationService: LocationService, private router: Router, private userService: UserService, private toastr: ToastrService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.profileType = this.authService.getTokenRole();
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
    );
    this.userService.getUserProfilePhoto().subscribe(data => {
      this.userProfileImg = data;
    })
    this.initLoyalty();
  }

  onCountryChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.personalDetailsForm.get('country')?.value);
    this.personalDetailsForm.controls.city.setValue('');
  }

  private initializeForms(): void {
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

  insertUserData(): void {
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
      return { noMatch: true };
    }
    return null;
  }

  getPasswordsControl() {
    return (this.securityDetailsForm.get('passwords') as FormGroup).controls;
  }

  confirmPasswordErrorMessage(): string {
    if (this.getPasswordsControl().confirmPassword.touched) {
      if (this.getPasswordsControl().confirmPassword.hasError('required')) {
        return 'You must confirm your password';
      } else if (this.securityDetailsForm.get('passwords')?.hasError('noMatch')) {
        return 'Passwords do not match';
      }
    }
    return "";
  }

  updateProfile(): void {
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

  changePassword(): void {
    if (this.securityDetailsForm.valid) {
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

  onFileChanged(event: any): void {
    const file = event.target.files[0]
    const mimeType = file.type;
    if (mimeType.match(/image\/*/) == null) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        title: "Only images are supported",
        message: "Please select an image to upload",
        buttonText: "Okay"
      };
      const dialogRef = this.dialog.open(InfoDialogComponent, dialogConfig);
      return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (_event) => {
      this.newProfileImg = reader.result;
    }

    const image = new FormData();
    image.append('file', file);
    this.userService.updateProfilePhoto(image).subscribe(
      data => {
        console.log("success");
      },
      error => {
        console.log(error);
      }
    );
  }

  deleteAccountPrompt() {
    const dialogConfig = new MatDialogConfig();
    this.dialog.open(DeleteAccountDialogComponent, dialogConfig);
  }

  initLoyalty() {
    if(this.profileType == "ROLE_CLIENT"){
      this.userService.getClientLevelMarks().subscribe(data => {
        this.levelMarks = data;
      });
    } else if(this.profileType != "ROLE_ADMIN"){
      this.userService.getSellerLevelMarks().subscribe(data => {
        this.levelMarks = data;
      });
    }
    this.userService.getPointsAchieved().subscribe(data => {
      this.points = data;
    });
  }

}


