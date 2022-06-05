import { Component } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';
import { EntityBasicInfo } from 'src/app/shared/models/entity-basic-info.model';
import { EntityService } from './services/entity.service';
import * as _ from 'lodash';
import { ToastrService } from 'ngx-toastr';
import { AdventureService } from 'src/app/shared/services/adventure.service';
import { BoatService } from 'src/app/shared/services/boat.service';
import { HouseService } from 'src/app/shared/services/house.service';

@Component({
  selector: 'app-entities',
  templateUrl: './entities.component.html',
  styleUrls: ['./entities.component.scss']
})
export class EntitiesComponent {

  entityType: string = "";
  entitiesBasicInfo!: Array<EntityBasicInfo>;
  searchedEntitiesBasicInfo!: Array<EntityBasicInfo>;
  filteredEntitiesBasicInfo!: Array<EntityBasicInfo>;
  cities!: Array<string>;
  isSearchApplied: boolean = false;
  maxPrice?: number;

  constructor(private entityService: EntityService, private router: Router, private adventureService: AdventureService, private boatService: BoatService, private houseService: HouseService, private toastr: ToastrService) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.entityType = this.router.url.substring(1);
        this.fetchEntitiesBasicInfo();
      }
    });
  }

  fetchEntitiesBasicInfo() {
    this.entityService.fetchEntitiesBasicInfo(this.entityType).subscribe(
      data => {
        this.entitiesBasicInfo = data;
        this.filteredEntitiesBasicInfo = data;
        this.searchedEntitiesBasicInfo = data;
        this.extractCities();
        this.findMaxPrice();
      }
    );
  }

  findMaxPrice() {
    this.maxPrice = _.maxBy(this.entitiesBasicInfo, function (o) { return o.price })?.price;
  }

  extractCities() {
    this.cities = [...new Set(this.entitiesBasicInfo.map(entity => entity.city))];
  }

  onSearchByRentalDetails(rentalDetails: any) {
    if (rentalDetails.location) {
      this.searchedEntitiesBasicInfo = this.entitiesBasicInfo.filter(item => {
        return item.city.toLowerCase().indexOf(rentalDetails.location.toLowerCase()) > -1 || item.country.toLowerCase().indexOf(rentalDetails.location.toLowerCase()) > -1
      });
    }
    if (rentalDetails.name) {
      this.searchedEntitiesBasicInfo = this.searchedEntitiesBasicInfo.filter(item => item.name.toLowerCase().indexOf(rentalDetails.name.toLowerCase()) > -1);
    }
    if (rentalDetails.range) {
      console.log(rentalDetails.range); // Filter by date range
    }
    this.filteredEntitiesBasicInfo = this.searchedEntitiesBasicInfo;
    this.isSearchApplied = true;
  }

  onFilterSelectionChanged(selectedFilters: any) {
    if (selectedFilters.selectedCities.length == 0 && selectedFilters.selectedStars.length == 0 && (!selectedFilters.selectedPrice || selectedFilters.selectedPrice == 0)) this.filteredEntitiesBasicInfo = this.searchedEntitiesBasicInfo;
    else {
      let filteredByCities;
      let filteredByPrice;
      let filteredByStars;
      if (selectedFilters.selectedPrice && selectedFilters.selectedPrice != 0)
        filteredByPrice = this.searchedEntitiesBasicInfo.filter(item => selectedFilters.selectedPrice >= item.price);
      if (selectedFilters.selectedCities.length != 0)
        filteredByCities = this.searchedEntitiesBasicInfo.filter(item => _.includes(selectedFilters.selectedCities, item.city));
      if (selectedFilters.selectedStars.length != 0)
        filteredByStars = this.searchedEntitiesBasicInfo.filter(item => {
          if (!item.rating)
            return true;
          else
            return _.includes(selectedFilters.selectedStars, item.rating)
        });
      this.filteredEntitiesBasicInfo = _.unionBy(filteredByCities, filteredByStars, filteredByPrice, _.property('id'));
    }
  }

  removeSearchResults() {
    this.filteredEntitiesBasicInfo = this.entitiesBasicInfo;
    this.searchedEntitiesBasicInfo = this.entitiesBasicInfo;
    this.isSearchApplied = false;
  }

  deleteEntity(id: number) {
    if(this.entityType == 'adventures'){
      this.adventureService.deleteAdventure(id).subscribe(data => {
        this.entitiesBasicInfo = this.entitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.searchedEntitiesBasicInfo = this.searchedEntitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.filteredEntitiesBasicInfo = this.filteredEntitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
      }, error => {
        this.toastr.error(error.error.message);
      })
    }
    if(this.entityType == 'boats'){
      this.boatService.deleteBoat(id).subscribe(data => {
        this.entitiesBasicInfo = this.entitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.searchedEntitiesBasicInfo = this.searchedEntitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.filteredEntitiesBasicInfo = this.filteredEntitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
      }, error => {
        this.toastr.error(error.error.message);
      })
    }
    if(this.entityType == 'houses'){
      this.houseService.deleteHouse(id).subscribe(data => {
        this.entitiesBasicInfo = this.entitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.searchedEntitiesBasicInfo = this.searchedEntitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.filteredEntitiesBasicInfo = this.filteredEntitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
      }, error => {
        this.toastr.error(error.error.message);
      })
    }
  }
}
