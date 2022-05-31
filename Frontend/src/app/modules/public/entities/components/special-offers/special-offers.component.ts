import { Component, Input, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { SpecialOffer } from '../../models/special-offer.model';
import { SubscriptionService } from '../../services/subscription.service';

@Component({
  selector: 'entity-special-offers',
  templateUrl: './special-offers.component.html',
  styleUrls: ['./special-offers.component.scss']
})
export class SpecialOffersComponent implements OnInit {

  isSubscribed!: boolean;

  constructor(private subscriptionService: SubscriptionService, private authService: AuthService) { }

  ngOnInit(): void {
    this.subscriptionService.checkSubscriptionStatus(this.entityId).subscribe(data => {
      this.isSubscribed = data;
    })
  }

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
  @Input() entityId!: number;

  toggleSubscribe() {
    let subscription = { entityId: this.entityId }
    this.subscriptionService.toggleSubscribe(subscription).subscribe(
      data => {
        this.isSubscribed = !this.isSubscribed;
      },
      error => {

      }
    )
  }

  loggedIn() {
    return this.authService.isLoggedIn();
  }

}
