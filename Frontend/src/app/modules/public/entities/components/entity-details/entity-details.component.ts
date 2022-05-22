import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, Event, NavigationEnd, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { EntityDetailsService } from '../../services/entity-details.service';

@Component({
  selector: 'app-entity-details',
  templateUrl: './entity-details.component.html',
  styleUrls: ['./entity-details.component.scss']
})
export class EntityDetailsComponent implements OnDestroy, OnInit {

  entityType: string = "";
  entityDetails!: any;
  ownerUsername: string = "";
  loggedInUserUsername: string = "";
  private routerSub: Subscription;

  constructor(private router: Router, private route: ActivatedRoute, private entityDetailsService: EntityDetailsService, private authService: AuthService) {
    this.routerSub = router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes('houses')){
          this.entityType = "house";
        } else if(this.router.url.includes('boats')){
          this.entityType = "boat";
        } else if(this.router.url.includes('adventures')){
          this.entityType = "adventure";
        }
        this.fetchEntitiyDetails();
      }
    });
  }
  ngOnInit(): void {
    this.loggedInUserUsername = this.authService.getTokenUsername();
  }

  fetchEntitiyDetails() {
    let entityId = this.route.snapshot.params['id'];
    if(this.entityType == 'house')
      this.entityDetailsService.fetchHouseDetails(entityId).subscribe(data => {
        this.entityDetails = data;
        this.ownerUsername = this.entityDetails.sellerEmail;
      });
    else if(this.entityType == 'boat'){
      this.entityDetailsService.fetchBoatDetails(entityId).subscribe(data => {
        this.entityDetails = data;
        this.ownerUsername = this.entityDetails.sellerEmail;
      });
    } else if(this.entityType == 'adventure'){
      this.entityDetailsService.fetchAdventureDetails(entityId).subscribe(data => {
        this.entityDetails = data;
        this.ownerUsername = this.entityDetails.sellerEmail;
      });
    }
  }

  ngOnDestroy() {
    this.routerSub.unsubscribe();
  }

}
