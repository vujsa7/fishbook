import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { PasswordRenewalService } from 'src/app/modules/admin/services/password-renewal.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  messageDialogTitle: string = "";
  messageDialogMessage: string = "";
  messageDialogButtonText: string = "";
  isMessageDialogVisible: boolean = false;

  constructor(private authService: AuthService, private passwordRenewalService: PasswordRenewalService, private route: Router) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void{
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  onSubmit() : void{
    if(this.loginForm.valid){
      this.authService.login(this.loginForm.value).subscribe(
        data => {
          this.authService.setToken(data.accessToken);
          if(this.authService.getTokenRole() == 'ROLE_ADMIN'){
            this.navigateToAdminModule();
          }else{
            this.route.navigate(["/homepage"]);
          }
        },
        error => {
          if(error.status == 401)
            this.showMessageDialog("Incorrect credentials", "The email and password you entered didn't match our records. Please try again.", "Okay");
        }
      );
    }
  }

  showMessageDialog(title: string, message: string, buttonText: string) {
    this.messageDialogTitle = title;
    this.messageDialogMessage = message;
    this.messageDialogButtonText = buttonText;
    this.isMessageDialogVisible = true;
  }

  onMessageDialogNotify(message: string): void{
    if(message == "close")
      this.isMessageDialogVisible = false;
  }

  navigateToAdminModule(): void {
    let username = this.authService.getTokenUsername();

    this.passwordRenewalService.getPasswordRenewalMark(username).subscribe(
      data => {
        this.route.navigate(["/admin/password-renewal"]);
      },
      error => {
        if(error.status == 404){
          this.route.navigate(["/admin/business"]);
        }
      }
    );
  }

}
