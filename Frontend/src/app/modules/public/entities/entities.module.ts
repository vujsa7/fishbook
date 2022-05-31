import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EntitiesRoutingModule } from './entities-routing.module';
import { EntitiesComponent } from './entities.component';
import { RentalDetailsComponent } from './components/rental-details/rental-details.component';
import { FilterSearchResultsComponent } from './components/filter-search-results/filter-search-results.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PriceSliderComponent } from './components/price-slider/price-slider.component';
import { EntityDetailsComponent } from './components/entity-details/entity-details.component';
import { HouseModule } from './house/house.module';
import { ImagesViewerComponent } from './components/images-viewer/images-viewer.component';
import { AdventureModule } from './adventure/adventure.module';
import { BoatModule } from './boat/boat.module';
import { SpecialOffersComponent } from './components/special-offers/special-offers.component';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EntityDetailsService } from './services/entity-details.service';
import { EntityService } from './services/entity.service';
import { SubscriptionService } from '../../client/dashboard/services/subscription.service';

@NgModule({
  declarations: [
    EntitiesComponent,
    RentalDetailsComponent,
    FilterSearchResultsComponent,
    PriceSliderComponent,
    EntityDetailsComponent,
    ImagesViewerComponent,
    SpecialOffersComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    EntitiesRoutingModule,
    HouseModule,
    BoatModule,
    AdventureModule,
    CarouselModule ,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [
    EntityService,
    EntityDetailsService,
    SubscriptionService
  ]
})
export class EntitiesModule { }
