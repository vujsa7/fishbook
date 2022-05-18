import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { PasswordRenewalService } from 'src/app/modules/admin/services/password-renewal.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  constructor(private authService: AuthService, private passwordRenewalService: PasswordRenewalService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(
        data => {
          this.authService.setToken(data.accessToken);
          let tokenRole = this.authService.getTokenRole();
          if (tokenRole == 'ROLE_ADMIN') {
            this.navigateToAdminModule();
          } else if(tokenRole == 'ROLE_CLIENT'){
            this.router.navigate(["/client/homepage"]);
          } 
          else {
            this.router.navigate(["/homepage"]);
          }
        },
        error => {
          const dialogConfig = new MatDialogConfig();
          if (error.status == 401) {
            dialogConfig.data = {
              title: "Incorrect credentials",
              message: "The email and password you entered didn't match our records. Please try again",
              buttonText: "Okay"
            };
          } else if(error.status == 422){
            dialogConfig.data = {
              title: "Account is currently disabled",
              message: "This account is currently disabled, you will receive a message on an email associated with your account from our system administrator regarding your account. Thank you for your patience.",
              buttonText: "Okay"
            };
          }
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    }
  }

  navigateToAdminModule(): void {
    let username = this.authService.getTokenUsername();

    this.passwordRenewalService.getPasswordRenewalMark(username).subscribe(
      data => {
        this.router.navigate(["/admin/password-renewal"]);
      },
      error => {
        if (error.status == 404) {
          this.router.navigate(["/admin/business"]);
        }
      }
    );
  }

}
