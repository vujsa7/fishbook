import { Component } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';
import { EntityBasicInfo } from 'src/app/shared/models/entity-basic-info.model';
import { EntityService } from './services/entity.service';

@Component({
  selector: 'app-entities',
  templateUrl: './entities.component.html',
  styleUrls: ['./entities.component.scss']
})
export class EntitiesComponent {

  isLoadingEntities: boolean = true;
  entitiesBasicInfo!: Array<EntityBasicInfo>;
  entityType: string = "";

  constructor(private entityService: EntityService, private router: Router) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.entityType = this.router.url.substring(1);
        this.isLoadingEntities = true;
        this.fetchEntitiesBasicInfo();
        setTimeout(() => {
          this.isLoadingEntities = false;
        }, 500);
      }
    });
  }

  fetchEntitiesBasicInfo() {
    this.entitiesBasicInfo = this.entityService.fetchEntitiesBasicInfo(this.entityType);
  }

}
