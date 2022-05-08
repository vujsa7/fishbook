import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, Event } from '@angular/router';
import { Address } from 'src/app/models/location/address.model';
import { City } from 'src/app/models/location/city.model';
import { AdventureRegistrationRequest } from '../../models/adventure-registration-request.model';
import { BoatRegistrationRequest } from '../../models/boat-registration-request';

@Component({
  selector: 'app-new-entity',
  templateUrl: './new-entity.component.html',
  styleUrls: ['./new-entity.component.scss']
})
export class NewEntityComponent implements OnInit {
  selectedButton: string = 'general';
  entityRegistrationRequest: any;
  entityType: string = "";

  constructor(private router: Router) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes('new-boat')){
          this.entityType = "boat";
          this.entityRegistrationRequest = new BoatRegistrationRequest("", "", "", "", -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, "");
        }
        if(this.router.url.includes('new-house')){
          this.entityType = "house";
        }
        if(this.router.url.includes('new-adventure')){
          this.entityType = "adventure";
          this.entityRegistrationRequest = new AdventureRegistrationRequest("", "", new Address("", new City()), -1, -1, -1, [], [], [], "");
        }
      }
    });
   }

  ngOnInit(): void {
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
