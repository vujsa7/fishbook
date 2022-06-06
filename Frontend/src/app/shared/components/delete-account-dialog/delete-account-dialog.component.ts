import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { UserService } from '../../services/user.service';
import { InfoDialogComponent } from '../info-dialog/info-dialog.component';


@Component({
  selector: 'app-delete-account-dialog',
  templateUrl: './delete-account-dialog.component.html',
  styleUrls: ['./delete-account-dialog.component.scss']
})
export class DeleteAccountDialogComponent implements OnInit{

  deleteAccountForm!: FormGroup;

  constructor(private dialogRef: MatDialogRef<DeleteAccountDialogComponent>, private userService: UserService, private dialog: MatDialog, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.deleteAccountForm = new FormGroup({
      deleteRequestMessage: new FormControl('', [Validators.required]),
    });
  }

  onSubmit(): void {
    if (this.deleteAccountForm.valid) {
      this.userService.postDeleteAccountRequest(this.deleteAccountForm.get("deleteRequestMessage")?.value).subscribe(
        _ => {
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Account scheduled for deletion",
            message: "You have scheduled the deletion of your account. Once the system administrator approves deletion request you will receive an email.",
            buttonText: "Okay"
          };
          this.authService.flushToken();
          this.router.navigate(["/"]);
          this.dialog.open(InfoDialogComponent, dialogConfig);
        },
        _ => {
          this.dialogRef.close();
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Unable to delete",
            message: _.error.message,
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }
}
