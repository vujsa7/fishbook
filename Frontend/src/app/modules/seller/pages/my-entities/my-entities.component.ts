import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NavigationEnd, Router, Event } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EntityBasicInfo } from 'src/app/shared/models/entity-basic-info.model';
import { AdventureService } from 'src/app/shared/services/adventure.service';
import { BoatService } from 'src/app/shared/services/boat.service';
import { HouseService } from 'src/app/shared/services/house.service';

@Component({
  selector: 'app-my-entities',
  templateUrl: './my-entities.component.html',
  styleUrls: ['./my-entities.component.scss']
})
export class MyEntitiesComponent implements OnInit {
  entityType: string = "";
  entitiesBasicInfo!: Array<EntityBasicInfo>;
  searchedEntitiesBasicInfo!: Array<EntityBasicInfo>;
  entitySearchForm!: FormGroup;
  isSearchApplied: boolean = false; 

  constructor(private router: Router, private adventureService: AdventureService, private boatService: BoatService, private houseService: HouseService, private toastr: ToastrService) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes('my-boats')){
          this.entityType = "boat";
          this.boatService.getBoatsForOwner().subscribe(data => {
            this.entitiesBasicInfo = data;
            this.searchedEntitiesBasicInfo = data;
          }, error => {
            this.toastr.error("Error loading entities");
          })
        }
        if(this.router.url.includes('my-houses')){
          this.entityType = "house";
          this.houseService.getHousesForOwner().subscribe(data => {
            this.entitiesBasicInfo = data;
            this.searchedEntitiesBasicInfo = data;
          }, error => {
            this.toastr.error("Error loading entities");
          })
        }
      }
    });    
  }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.entitySearchForm = new FormGroup({
      location: new FormControl(''),
      name: new FormControl('')
    });
  }

  deleteEntity(id: number) {
    if(this.entityType == 'boat'){
      this.boatService.deleteBoat(id).subscribe(data => {
        this.entitiesBasicInfo = this.entitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.searchedEntitiesBasicInfo = this.entitiesBasicInfo;
      }, error => {
        this.toastr.error("Error deleting entity");
      })
    }
    if(this.entityType == 'house'){
      this.houseService.deleteHouse(id).subscribe(data => {
        this.entitiesBasicInfo = this.entitiesBasicInfo.filter((entity: { id: number; }) => entity.id != id);
        this.searchedEntitiesBasicInfo = this.entitiesBasicInfo;
      }, error => {
        this.toastr.error("Error deleting entity");
      })
    }
  }

  addEntity() {
    if(this.entityType == 'boat'){
      this.router.navigate(["/seller/new-boat"]);
    }
    if(this.entityType == 'house'){
      this.router.navigate(["/seller/new-house"]);
    }
  }

  onSubmit(): void {
    if (this.entitySearchForm.controls.location.value) {
      this.searchedEntitiesBasicInfo = this.entitiesBasicInfo.filter(item => {
        return item.city.toLowerCase().indexOf(this.entitySearchForm.controls.location.value.toLowerCase()) > -1 || item.country.toLowerCase().indexOf(this.entitySearchForm.controls.location.value.toLowerCase()) > -1
      });
    }
    if (this.entitySearchForm.controls.name.value) {
      this.searchedEntitiesBasicInfo = this.searchedEntitiesBasicInfo.filter(item => item.name.toLowerCase().indexOf(this.entitySearchForm.controls.name.value.toLowerCase()) > -1);
    }
    this.isSearchApplied = true;
  }

  removeSearchResults() {
    this.searchedEntitiesBasicInfo = this.entitiesBasicInfo;
    this.isSearchApplied = false;
  }

}
