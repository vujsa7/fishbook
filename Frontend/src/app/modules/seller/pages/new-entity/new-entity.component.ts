import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, Event, ActivatedRoute } from '@angular/router';
import { Address } from 'src/app/models/location/address.model';
import { City } from 'src/app/models/location/city.model';
import { AdventureService } from 'src/app/shared/services/adventure.service';
import { BoatService } from 'src/app/shared/services/boat.service';
import { AdventureRegistrationRequest } from '../../models/adventure-registration-request.model';
import { AdventureUpdateRequest } from '../../models/adventure-update-request.model';
import { BoatRegistrationRequest } from '../../models/boat-registration-request.model';
import { BoatUpdateRequest } from '../../models/boat-update-request.model';
import { HouseRegistrationRequest } from '../../models/house-registration-request.model';

@Component({
  selector: 'app-new-entity',
  templateUrl: './new-entity.component.html',
  styleUrls: ['./new-entity.component.scss']
})
export class NewEntityComponent implements OnInit {
  selectedButton: string = 'general';
  entityRegistrationRequest: any;
  entityUpdateRequest: any;
  entityType: string = "";
  edit: boolean = false;
  entityLoaded: boolean = false;

  constructor(private router: Router, private route: ActivatedRoute, private adventureService: AdventureService, private boatService: BoatService) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes('boat')){
          this.entityType = "boat";
          this.entityRegistrationRequest = new BoatRegistrationRequest("", "", "", "", -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, "");
          if(this.router.url.includes('edit')){
            this.edit = true;
            let id = +this.route.snapshot.params['id'];
            this.boatService.fetchBoatDetails(id).subscribe(data => {
              this.entityUpdateRequest = new BoatUpdateRequest(id, data.name, data.description, data.location.address, data.location.city, data.location.country, data.boatSpecifications.length, data.boatSpecifications.motorsOnBoat, data.boatSpecifications.horsepower, data.boatSpecifications.maximumSpeed, data.boatSpecifications.maxPeople, data.boatSpecifications.loadCapacity, data.boatSpecifications.fuelConsumption, data.boatSpecifications.maxDistanceOnTank, data.boatSpecifications.energyConsumption, data.cancellationFee, data.price, data.boatSpecifications.boatType, data.rules, data.fishingEquipment, data.navigationEquipment);
              this.entityLoaded = true;
            })
          }
        }
        if(this.router.url.includes('house')){
          this.entityType = "house";
          this.entityRegistrationRequest = new HouseRegistrationRequest("", "", "", "", -1, -1, -1);
        }
        if(this.router.url.includes('adventure')){
          this.entityType = "adventure";
          this.entityRegistrationRequest = new AdventureRegistrationRequest("", "", new Address("", new City()), -1, -1, -1, [], [], [], "");
          if(this.router.url.includes('edit')){
            this.edit = true;
            let id = +this.route.snapshot.params['id'];
            this.adventureService.fetchAdventureDetails(id).subscribe(data => {
              this.entityUpdateRequest = new AdventureUpdateRequest(id, data.name, data.description, data.location.address, data.location.city, data.location.country, data.maxPeople, data.cancellationFee, data.price, data.rules, data.fishingEquipment, new Array(), data.aboutSeller);
              this.entityLoaded = true;
            })
          }
        }
      }
    });
   }

  ngOnInit(): void {
    if(!this.edit){
      this.entityLoaded = true;
    }
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

  addGeneralInfo(){
    this.selectedButton = "specifications";
    window.scroll({ 
      top: 0, 
      left: 0, 
      behavior: 'smooth' 
    });
  }

  addSpecification(){
    this.selectedButton = "pricing";
    window.scroll({ 
      top: 0, 
      left: 0, 
      behavior: 'smooth' 
    });
  }

  addPricing(){
    this.selectedButton = "gallery";
    window.scroll({ 
      top: 0, 
      left: 0, 
      behavior: 'smooth' 
    });
  }

}
