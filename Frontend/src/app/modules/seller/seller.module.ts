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
import { AdventureDetailsComponent } from './pages/new-entity/adventure-details/adventure-details.component';
import { HouseDetailsComponent } from './pages/new-entity/house-details/house-details.component';
import { MyEntitiesComponent } from './pages/my-entities/my-entities.component';

@NgModule({
  declarations: [
    SellerComponent,
    NewEntityComponent,
    GeneralComponent,
    BoatSpecComponent,
    PricingComponent,
    GalleryComponent,
    AdventureDetailsComponent,
    HouseDetailsComponent,
    MyEntitiesComponent
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
