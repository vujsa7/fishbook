import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Equipment } from 'src/app/shared/models/equipment.model';
import { BoatService } from 'src/app/shared/services/boat.service';
import { BoatRegistrationRequest } from '../../../models/boat-registration-request';

@Component({
  selector: 'app-boat-spec',
  templateUrl: './boat-spec.component.html',
  styleUrls: ['./boat-spec.component.scss']
})
export class BoatSpecComponent implements OnInit {
  newBoatForm!: FormGroup;
  @Input() boatRegistrationRequest!: BoatRegistrationRequest;
  @Output() addBoatSpecificationEvent = new EventEmitter();
  navigationEquipment: Array<Equipment> = new Array();
  fishingEquipment: Array<Equipment> = new Array();

  constructor(private boatService: BoatService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.boatService.getBoatEquipment().subscribe(
      data => {
        this.sortEquipment(data);
      }
    )
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
      energyConsumption : new FormControl('', [Validators.required]),
      equipment : new FormArray([])
    })
  }

  private sortEquipment(equipment: Array<Equipment>){
    for (let equipmentItem of equipment) {
      if (equipmentItem.type == 'navigation') {
        this.navigationEquipment.push(equipmentItem);
      } else if (equipmentItem.type == 'fishing') {
        this.fishingEquipment.push(equipmentItem);
      }
    }
  }

  equipmentChanged(event: any, value: Equipment) {
    const selectedEquipment = (this.newBoatForm.controls.equipment as FormArray);
    if (event.target.checked) {
      selectedEquipment.push(new FormControl(value));
    } else {
      const index = selectedEquipment.controls
        .findIndex(x => x.value === value);
        selectedEquipment.removeAt(index);
    }
  }

  addBoatSpecification() {
    if(this.newBoatForm.valid){
      this.boatRegistrationRequest.boatType = this.newBoatForm.controls.boatType.value;
      this.boatRegistrationRequest.maxPeople = this.newBoatForm.controls.maxPeople.value;
      this.boatRegistrationRequest.length = this.newBoatForm.controls.length.value;
      this.boatRegistrationRequest.loadCapacity = this.newBoatForm.controls.loadCapacity.value;
      this.boatRegistrationRequest.maxSpeed = this.newBoatForm.controls.maxSpeed.value;
      this.boatRegistrationRequest.power = this.newBoatForm.controls.horsepower.value;
      this.boatRegistrationRequest.motors = this.newBoatForm.controls.motorsOnBoat.value;
      this.boatRegistrationRequest.fuelConsumption = this.newBoatForm.controls.fuelConsumption.value;
      this.boatRegistrationRequest.maxDistance = this.newBoatForm.controls.maxDistance.value;
      this.boatRegistrationRequest.energyConsumption = this.newBoatForm.controls.energyConsumption.value;
      this.boatRegistrationRequest.equipment = this.newBoatForm.controls.equipment?.value;
      this.addBoatSpecificationEvent.emit();
    }
  }

}
