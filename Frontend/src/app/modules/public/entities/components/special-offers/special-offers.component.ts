import { Component, Input } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { SpecialOffer } from '../../models/special-offer.model';

@Component({
  selector: 'entity-special-offers',
  templateUrl: './special-offers.component.html',
  styleUrls: ['./special-offers.component.scss']
})
export class SpecialOffersComponent {

  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: true,
    touchDrag: true,
    pullDrag: true,
    dots: true,
    responsive: {
      0: {
        items: 1
      },
      400: {
        items: 1.3
      },
      500: {
        items: 1.5
      },
      840: {
        items: 2
      },
      1100: {
        items: 3
      },
      1600: {
        items: 4
      }
    },
    margin: 50,
  }

  @Input() specialOffers!: Array<SpecialOffer>;

}
