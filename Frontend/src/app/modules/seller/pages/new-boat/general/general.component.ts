import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { BoatService } from 'src/app/shared/services/boat.service';
import { LocationService } from 'src/app/shared/services/location.service';
import { AppliedRule } from '../../../../../shared/models/applied-rule';
import { BoatRegistrationRequest } from '../../../models/boat-registration-request';


@Component({
  selector: 'app-general',
  templateUrl: './general.component.html',
  styleUrls: ['./general.component.scss']
})
export class GeneralComponent implements OnInit {
  newBoatForm!: FormGroup;
  @Input() boatRegistrationRequest!: BoatRegistrationRequest;
  @Output() addGeneralInfoEvent = new EventEmitter();
  cities: City[] = [];
  states: Country[] = [];
  filteredCities: City[] = [];
  appliedRules: Array<AppliedRule> = new Array();

  constructor(private locationService: LocationService, private boatService: BoatService) { }

  ngOnInit(): void {
    this.initializeForm();
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
    this.boatService.getBoatRules().subscribe(
      data => {
        this.appliedRules = data;
      }
    )
  }

  private initializeForm(): void{
    this.newBoatForm = new FormGroup({
      boatName: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      street : new FormControl('', [Validators.required]),
      city : new FormControl('', [Validators.required]),
      state : new FormControl('', [Validators.required]),
      appliedRules : new FormArray([])
    })
  }

  onStateChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.newBoatForm.get('state')?.value);
    this.newBoatForm.controls.city.setValue('');
  }

  appliedRulesChanged(event: any, value: AppliedRule) {
    const selectedAppliedRules = (this.newBoatForm.controls.appliedRules as FormArray);
    if (event.target.checked) {
      selectedAppliedRules.push(new FormControl(value));
    } else {
      const index = selectedAppliedRules.controls
        .findIndex(x => x.value === value);
      selectedAppliedRules.removeAt(index);
    }
  }

  addGeneralInfo() {
    if(this.newBoatForm.valid){
      this.boatRegistrationRequest.name = this.newBoatForm.controls.boatName.value;
      this.boatRegistrationRequest.description = this.newBoatForm.controls.description.value;
      this.boatRegistrationRequest.address = this.newBoatForm.controls.street.value;
      this.boatRegistrationRequest.city = this.newBoatForm.controls.city.value;
      this.boatRegistrationRequest.appliedRules = this.newBoatForm.controls.appliedRules?.value;
      this.addGeneralInfoEvent.emit();
    }
  }
}
