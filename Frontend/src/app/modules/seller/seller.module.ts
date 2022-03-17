import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellerRoutingModule } from './seller-routing.module';
import { SellerComponent } from './seller.component';
import { NewBoatComponent } from './pages/new-boat/new-boat.component';
import { GeneralComponent } from './pages/new-boat/general/general.component';
import { BoatSpecComponent } from './pages/new-boat/boat-spec/boat-spec.component';
import { PricingComponent } from './pages/new-boat/pricing/pricing.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    SellerComponent,
    NewBoatComponent,
    GeneralComponent,
    BoatSpecComponent,
    PricingComponent
  ],
  imports: [
    CommonModule,
    SellerRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class SellerModule { }
