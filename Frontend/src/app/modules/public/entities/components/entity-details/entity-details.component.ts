import { Component, OnDestroy } from '@angular/core';
import { Router, Event, NavigationEnd, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { EntityDetailsService } from '../../services/entity-details.service';

@Component({
  selector: 'app-entity-details',
  templateUrl: './entity-details.component.html',
  styleUrls: ['./entity-details.component.scss']
})
export class EntityDetailsComponent implements OnDestroy{

  entityType: string = "";
  entityDetails!: any;
  private routerSub: Subscription;

  constructor(private router: Router, private route: ActivatedRoute, private entityDetailsService: EntityDetailsService) {
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

  fetchEntitiyDetails() {
    let entityId = this.route.snapshot.params['id'];
    if(this.entityType == 'house')
      this.entityDetails = this.entityDetailsService.fetchHouseDetails(entityId);
    else if(this.entityType == 'boat'){
      this.entityDetails = this.entityDetailsService.fetchBoatDetails(entityId);
    } else if(this.entityType == 'adventure'){
      this.entityDetails = this.entityDetailsService.fetchAdventureDetails(entityId);
    }
  }

  ngOnDestroy() {
    this.routerSub.unsubscribe();
  }

}
