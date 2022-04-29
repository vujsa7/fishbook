import { Component, OnInit } from '@angular/core';
import { BoatRegistrationRequest } from '../../models/boat-registration-request';

@Component({
  selector: 'app-new-boat',
  templateUrl: './new-boat.component.html',
  styleUrls: ['./new-boat.component.scss']
})
export class NewBoatComponent implements OnInit {
  selectedButton: string = 'general';
  boatRegistrationRequest: BoatRegistrationRequest = new BoatRegistrationRequest("", "", "", "", -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, "");

  constructor() { }

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

  addBoatSpecification(){
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
