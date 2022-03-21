import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordRenewalService } from '../../services/password-renewal.service';

@Component({
  selector: 'app-password-renewal',
  templateUrl: './password-renewal.component.html',
  styleUrls: ['./password-renewal.component.scss']
})
export class PasswordRenewalComponent implements OnInit {
  passwordRenewalForm!: FormGroup;

  constructor(private passwordRenewalService: PasswordRenewalService, private router: Router) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void{
    this.passwordRenewalForm = new FormGroup({
      passwords: new FormGroup({
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
      }, [this.passwordMatchValidator])
    });
  }

  passwordMatchValidator(c: AbstractControl) {
    if (c.get('password')?.value != c.get('confirmPassword')?.value) {
      return {noMatch: true};
    }
    return null;
  }

  getPasswordsControl(){
    return (this.passwordRenewalForm.get('passwords') as FormGroup).controls;
  }

  confirmPasswordErrorMessage() : string {
    if(this.getPasswordsControl().confirmPassword.touched){
      if (this.getPasswordsControl().confirmPassword.hasError('required')) {
        return 'You must confirm your password';
      } else if (this.passwordRenewalForm.get('passwords')?.hasError('noMatch')) {
        return 'Passwords do not match';
      } 
    }
    return "";
  }

  onSubmit(): void {
    let passwordRenewal = {
      password: this.getPasswordsControl().confirmPassword?.value
    }
    this.passwordRenewalService.renewPassword(passwordRenewal).subscribe(
      data => {
        this.router.navigate(['/admin/business']);
      },
      error => {
        alert(error.message);
      }
    );
  }

}
