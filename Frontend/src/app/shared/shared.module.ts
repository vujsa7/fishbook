import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { UserTypePipe } from './pipes/user-type.pipe';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { MessageDialogComponent } from './components/message-dialog/message-dialog.component';
import { GlassContainerComponent } from './components/glass-container/glass-container.component';
import { DatePickerComponent } from './components/date-picker/date-picker.component';
import { MaterialModule } from './material/material.module';
import { EntityCardComponent } from './components/entity-card/entity-card.component';
import { SpinnerFullscreenComponent } from './layout/spinner-fullscreen/spinner-fullscreen.component';
import { ProfileComponent } from './components/profile/profile.component';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    InfoDialogComponent,
    DatePickerComponent,
    EntityCardComponent,
    SpinnerFullscreenComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    InfoDialogComponent,
    DatePickerComponent,
    EntityCardComponent,
    SpinnerFullscreenComponent,
    MaterialModule
  ]
})
export class SharedModule { }
