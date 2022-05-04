import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellerRoutingModule } from './seller-routing.module';
import { SellerComponent } from './seller.component';
import { NewEntityComponent } from './pages/new-entity/new-entity.component';
import { GeneralComponent } from './pages/new-entity/general/general.component';
import { BoatSpecComponent } from './pages/new-entity/boat-spec/boat-spec.component';
import { PricingComponent } from './pages/new-entity/pricing/pricing.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GalleryComponent } from './pages/new-entity/gallery/gallery.component';

@NgModule({
  declarations: [
    SellerComponent,
    NewEntityComponent,
    GeneralComponent,
    BoatSpecComponent,
    PricingComponent,
    GalleryComponent
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
