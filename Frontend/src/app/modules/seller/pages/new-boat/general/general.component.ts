import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { City } from 'src/app/models/location/city.model';
import { Country } from 'src/app/models/location/country.model';
import { LocationService } from 'src/app/shared/services/location.service';

@Component({
  selector: 'app-general',
  templateUrl: './general.component.html',
  styleUrls: ['./general.component.scss']
})
export class GeneralComponent implements OnInit {
  newBoatForm!: FormGroup;
  cities: City[] = [];
  states: Country[] = [];
  filteredCities: City[] = [];

  constructor(private locationService: LocationService) { }

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
  }

  private initializeForm(): void{
    this.newBoatForm = new FormGroup({
      boatName: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      street : new FormControl('', [Validators.required]),
      city : new FormControl('', [Validators.required]),
      state : new FormControl('', [Validators.required])
    })
  }

  onStateChanged() {
    this.filteredCities = this.cities.filter(c => c.country.name == this.newBoatForm.get('state')?.value);
    this.newBoatForm.controls.city.setValue('');
  }

}
