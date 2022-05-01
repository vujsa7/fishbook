import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalDetailsComponent } from './components/rental-details/rental-details.component';

@NgModule({
  declarations: [
    RentalDetailsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    RentalDetailsComponent
  ]
})
export class BoatModule { }
