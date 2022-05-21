import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientRoutingModule } from './client-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ReservationService } from './services/reservation.service';

@NgModule({
  declarations: [
   
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    SharedModule
  ],
  providers: [
    ReservationService
  ]
})
export class ClientModule { }
