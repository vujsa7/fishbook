import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { UserTypePipe } from './pipes/user-type.pipe';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { MessageDialogComponent } from './components/message-dialog/message-dialog.component';

@NgModule({
  declarations: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
  ]
})
export class SharedModule { }
