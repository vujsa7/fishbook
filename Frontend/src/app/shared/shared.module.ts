import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { UserTypePipe } from './pipes/user-type.pipe';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { MessageDialogComponent } from './components/message-dialog/message-dialog.component';
import { GlassContainerComponent } from './components/glass-container/glass-container.component';
import { ProfileComponent } from './components/profile/profile.component';

@NgModule({
  declarations: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent
  ]
})
export class SharedModule { }
