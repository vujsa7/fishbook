import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalDetailsComponent } from './components/rental-details/rental-details.component';
import { AboutComponent } from './components/about/about.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { GalleryComponent } from './components/gallery/gallery.component';

@NgModule({
  declarations: [
    RentalDetailsComponent,
    AboutComponent,
    GalleryComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    RentalDetailsComponent,
    AboutComponent,
    GalleryComponent
  ]
})
export class AdventureModule { }
