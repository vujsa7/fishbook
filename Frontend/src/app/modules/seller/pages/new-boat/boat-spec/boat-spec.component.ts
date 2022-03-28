import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-boat-spec',
  templateUrl: './boat-spec.component.html',
  styleUrls: ['./boat-spec.component.scss']
})
export class BoatSpecComponent implements OnInit {
  newBoatForm!: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.initializeForm();
  }
  
  private initializeForm() {
    this.newBoatForm = new FormGroup({
      boatType: new FormControl('', [Validators.required]),
      maxPeople : new FormControl('', [Validators.required]),
      length : new FormControl('', [Validators.required]),
      loadCapacity : new FormControl('', [Validators.required]),
      maxSpeed : new FormControl('', [Validators.required]),
      horsepower : new FormControl('', [Validators.required]),
      motorsOnBoat: new FormControl('', [Validators.required]),
      fuelConsumption : new FormControl('', [Validators.required]),
      maxDistance : new FormControl('', [Validators.required]),
      energyConsumption : new FormControl('', [Validators.required])
    })
  }

}
