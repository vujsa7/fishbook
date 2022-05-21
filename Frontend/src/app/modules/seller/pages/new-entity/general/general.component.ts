import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { LocationService } from 'src/app/shared/services/location.service';
import { Rule } from '../../../../../shared/models/rule.model';
import { RuleService } from '../../../services/rule.service';


@Component({
  selector: 'app-general',
  templateUrl: './general.component.html',
  styleUrls: ['./general.component.scss']
})
export class GeneralComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Input() entityType!: string;
  @Input() edit!: boolean;
  @Input() entityUpdateRequest!: any;
  @Output() addGeneralInfoEvent = new EventEmitter();
  cities: City[] = [];
  states: Country[] = [];
  filteredCities: City[] = [];
  appliedRules: Array<Rule> = new Array();

  constructor(private locationService: LocationService, private ruleService: RuleService) { }

  ngOnInit(): void {
    this.initializeForm();

    if(this.entityType == "adventure"){
      this.ruleService.getRules("fishingLesson").subscribe(
        data => {
          this.appliedRules = data;
          if(this.edit){
            this.initializeUpdateForm();
            const selectedAppliedRules = (this.newEntityForm.controls.appliedRules as FormArray);
            for(let rule of this.entityUpdateRequest.rules) {
              selectedAppliedRules.push(new FormControl(this.appliedRules.filter(r => r.description == rule)[0]));
            }
          }
        }
      )
      
    }
    
    this.locationService.getCountries().subscribe(
      data => {
        this.states = data;
      }
    )
    this.locationService.getCities().subscribe(
      data => {
        this.cities = data;
        this.filteredCities = data;
      }
    )
    if (this.entityType == "boat") {
      this.ruleService.getRules("boat").subscribe(
        data => {
          this.appliedRules = data;
        }
      )
    }
    
    if(this.entityType == "house"){
      this.ruleService.getRules("house").subscribe(
        data => {
          this.appliedRules = data;
        }
      )
    }
  }

  private initializeForm(): void{
    this.newEntityForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      street : new FormControl('', [Validators.required]),
      city : new FormControl('', [Validators.required]),
      state : new FormControl('', [Validators.required]),
      appliedRules : new FormArray([])
    })
  }

  private initializeUpdateForm(): void {
    this.newEntityForm.get('name')?.setValue(this.entityUpdateRequest.name);
    this.newEntityForm.get('description')?.setValue(this.entityUpdateRequest.description);
    this.newEntityForm.get('street')?.setValue(this.entityUpdateRequest.address);
    this.newEntityForm.get('city')?.setValue(this.entityUpdateRequest.city);
    this.newEntityForm.get('state')?.setValue(this.entityUpdateRequest.country);
  }

  onStateChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.newEntityForm.get('state')?.value);
    this.newEntityForm.controls.city.setValue('');
  }

  appliedRulesChanged(event: any, value: Rule) {
    const selectedAppliedRules = (this.newEntityForm.controls.appliedRules as FormArray);
    if (event.target.checked) {
      selectedAppliedRules.push(new FormControl(value));
    } else {
      const index = selectedAppliedRules.controls
        .findIndex(x => x.value === value);
      selectedAppliedRules.removeAt(index);
    }
  }

  addGeneralInfo() {
    if(this.newEntityForm.valid){
      if(this.entityType == "boat") {
        this.addBoatInfo();
      }
      if(this.entityType == "adventure") {
        this.addAdventureInfo();
      }
      if(this.entityType == "house") {
        this.addHouseInfo();
      }
      this.addGeneralInfoEvent.emit();
    }
  }

  addBoatInfo(){
    this.entityRegistrationRequest.name = this.newEntityForm.controls.name.value;
    this.entityRegistrationRequest.description = this.newEntityForm.controls.description.value;
    this.entityRegistrationRequest.address = this.newEntityForm.controls.street.value;
    this.entityRegistrationRequest.city = this.newEntityForm.controls.city.value;
    this.entityRegistrationRequest.appliedRules = this.newEntityForm.controls.appliedRules?.value;
  }

  addAdventureInfo(){
    if(this.edit){
      this.entityUpdateRequest.name = this.newEntityForm.controls.name.value;
      this.entityUpdateRequest.description = this.newEntityForm.controls.description.value;
      this.entityUpdateRequest.address = {
        address: this.newEntityForm.get("street")?.value,
        city: this.cities.filter(c => c.name == this.newEntityForm.get("city")?.value)[0]
      }
      this.entityUpdateRequest.rules = this.newEntityForm.controls.appliedRules?.value;
    } else {
      this.entityRegistrationRequest.name = this.newEntityForm.controls.name.value;
      this.entityRegistrationRequest.description = this.newEntityForm.controls.description.value;
      this.entityRegistrationRequest.address = {
        address: this.newEntityForm.get("street")?.value,
        city: this.cities.filter(c => c.name == this.newEntityForm.get("city")?.value)[0]
      }
      this.entityRegistrationRequest.rules = this.newEntityForm.controls.appliedRules?.value;
    } 
  }

  addHouseInfo(){
    this.entityRegistrationRequest.name = this.newEntityForm.controls.name.value;
    this.entityRegistrationRequest.description = this.newEntityForm.controls.description.value;
    this.entityRegistrationRequest.address = this.newEntityForm.controls.street.value;
    this.entityRegistrationRequest.city = this.newEntityForm.controls.city.value;
    this.entityRegistrationRequest.appliedRules = this.newEntityForm.controls.appliedRules?.value;
  }
  
}
