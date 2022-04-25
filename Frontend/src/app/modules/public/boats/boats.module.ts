import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BoatsRoutingModule } from './boats-routing.module';
import { BoatsComponent } from './boats.component';
import { RentalDetailsComponent } from './components/rental-details/rental-details.component';
import { FilterSearchResultsComponent } from './components/filter-search-results/filter-search-results.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PriceSliderComponent } from './components/price-slider/price-slider.component';


@NgModule({
  declarations: [
    BoatsComponent,
    RentalDetailsComponent,
    FilterSearchResultsComponent,
    PriceSliderComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    BoatsRoutingModule,
  ]
})
export class BoatsModule { }
