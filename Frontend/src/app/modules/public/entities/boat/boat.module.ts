import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalDetailsComponent } from './components/rental-details/rental-details.component';
import { SpecificationsComponent } from './components/specifications/specifications.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [
    RentalDetailsComponent,
    SpecificationsComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    RentalDetailsComponent,
    SpecificationsComponent
  ]
})
export class BoatModule { }
