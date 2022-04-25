import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { UserTypePipe } from './pipes/user-type.pipe';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { MessageDialogComponent } from './components/message-dialog/message-dialog.component';
import { GlassContainerComponent } from './components/glass-container/glass-container.component';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import { DatePickerComponent } from './components/date-picker/date-picker.component';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    InfoDialogComponent,
    DatePickerComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    PrimaryButtonComponent,
    UserTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    InfoDialogComponent,
    DatePickerComponent,
    MaterialModule
  ]
})
export class SharedModule { }
