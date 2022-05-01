import { Component } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-entity-details',
  templateUrl: './entity-details.component.html',
  styleUrls: ['./entity-details.component.scss']
})
export class EntityDetailsComponent {

  isLoadingEntity: boolean = false;
  entityType: string = "";

  constructor(private router: Router) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes('houses')){
          this.entityType = "house";
        } else if(this.router.url.includes('boats')){
          this.entityType = "boat";
        } else if(this.router.url.includes('adventures')){
          this.entityType = "adventure";
        }
        this.isLoadingEntity = true;
        this.fetchEntitiyDetails();
        
        setTimeout(() => {
          this.isLoadingEntity = false;
        }, 500);
      }
    });
  }

  fetchEntitiyDetails() {
    console.log("Fetching!");
  }

}
