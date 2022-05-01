import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalDetailsComponent } from './components/rental-details/rental-details.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [
    RentalDetailsComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    RentalDetailsComponent
  ]
})
export class HouseModule { }
