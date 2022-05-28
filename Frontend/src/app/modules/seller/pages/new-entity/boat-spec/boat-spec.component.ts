import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Equipment } from 'src/app/shared/models/equipment.model';
import { EquipmentService } from '../../../services/equipment.service';

@Component({
  selector: 'app-boat-spec',
  templateUrl: './boat-spec.component.html',
  styleUrls: ['./boat-spec.component.scss']
})
export class BoatSpecComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Input() edit!: boolean;
  @Input() entityUpdateRequest!: any;
  @Output() addBoatSpecificationEvent = new EventEmitter();
  navigationEquipment: Array<Equipment> = new Array();
  fishingEquipment: Array<Equipment> = new Array();

  constructor(private equipmentService: EquipmentService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.equipmentService.getEquipment("boat").subscribe(
      data => {
        this.sortEquipment(data);
        if(this.edit){
          this.initializeUpdateForm();
        }
      }
    )
  }
  
  private initializeForm() {
    this.newEntityForm = new FormGroup({
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
    const selectedEquipment = (this.newEntityForm.controls.equipment as FormArray);
    if (event.target.checked) {
      selectedEquipment.push(new FormControl(value));
    } else {
      const index = selectedEquipment.controls
        .findIndex(x => x.value === value);
        selectedEquipment.removeAt(index);
    }
  }

  addEntityInfo() {
    if(this.newEntityForm.valid) {
      this.addBoatSpecification();
      this.addBoatSpecificationEvent.emit();
    }
  }

  addBoatSpecification() {
    if(this.edit){
      this.fillUpdateRequest();
    } else {
      this.fillRegistrationRequest();
    }
  }

  private fillUpdateRequest() : void {
    this.entityUpdateRequest.boatType = this.newEntityForm.controls.boatType.value;
    this.entityUpdateRequest.maxPeople = this.newEntityForm.controls.maxPeople.value;
    this.entityUpdateRequest.length = this.newEntityForm.controls.length.value;
    this.entityUpdateRequest.loadCapacity = this.newEntityForm.controls.loadCapacity.value;
    this.entityUpdateRequest.maxSpeed = this.newEntityForm.controls.maxSpeed.value;
    this.entityUpdateRequest.power = this.newEntityForm.controls.horsepower.value;
    this.entityUpdateRequest.motors = this.newEntityForm.controls.motorsOnBoat.value;
    this.entityUpdateRequest.fuelConsumption = this.newEntityForm.controls.fuelConsumption.value;
    this.entityUpdateRequest.maxDistance = this.newEntityForm.controls.maxDistance.value;
    this.entityUpdateRequest.energyConsumption = this.newEntityForm.controls.energyConsumption.value;
    this.entityUpdateRequest.equipment = this.newEntityForm.controls.equipment?.value;
  }

  private fillRegistrationRequest() : void {
    this.entityRegistrationRequest.boatType = this.newEntityForm.controls.boatType.value;
    this.entityRegistrationRequest.maxPeople = this.newEntityForm.controls.maxPeople.value;
    this.entityRegistrationRequest.length = this.newEntityForm.controls.length.value;
    this.entityRegistrationRequest.loadCapacity = this.newEntityForm.controls.loadCapacity.value;
    this.entityRegistrationRequest.maxSpeed = this.newEntityForm.controls.maxSpeed.value;
    this.entityRegistrationRequest.power = this.newEntityForm.controls.horsepower.value;
    this.entityRegistrationRequest.motors = this.newEntityForm.controls.motorsOnBoat.value;
    this.entityRegistrationRequest.fuelConsumption = this.newEntityForm.controls.fuelConsumption.value;
    this.entityRegistrationRequest.maxDistance = this.newEntityForm.controls.maxDistance.value;
    this.entityRegistrationRequest.energyConsumption = this.newEntityForm.controls.energyConsumption.value;
    this.entityRegistrationRequest.equipment = this.newEntityForm.controls.equipment?.value;
  }

  private initializeUpdateForm(): void {
    this.newEntityForm.get('boatType')?.setValue(this.transform(this.entityUpdateRequest.boatType));
    this.newEntityForm.get('maxPeople')?.setValue(this.entityUpdateRequest.maxPeople);
    this.newEntityForm.get('length')?.setValue(this.entityUpdateRequest.length);
    this.newEntityForm.get('loadCapacity')?.setValue(this.entityUpdateRequest.loadCapacity);
    this.newEntityForm.get('maxSpeed')?.setValue(this.entityUpdateRequest.maxSpeed);
    this.newEntityForm.get('horsepower')?.setValue(this.entityUpdateRequest.power);
    this.newEntityForm.get('motorsOnBoat')?.setValue(this.entityUpdateRequest.motors);
    this.newEntityForm.get('fuelConsumption')?.setValue(this.entityUpdateRequest.fuelConsumption);
    this.newEntityForm.get('maxDistance')?.setValue(this.entityUpdateRequest.maxDistance);
    this.newEntityForm.get('energyConsumption')?.setValue(this.entityUpdateRequest.energyConsumption);
  }

  private transform(value: string): string {
    if(value == 'Jet Ski'){
      return 'JET_SKI';
    }
    if(value == 'Inflatable boat'){
      return 'INFLATABLE_BOAT';
    }
    if(value == 'Airboat'){
      return 'AIRBOAT';
    }
    if(value == 'Speedboat'){
      return 'SPEEDBOAT';
    }
    if(value == 'Ferry'){
      return 'FERRY';
    }
    if(value == 'Small Yacht'){
      return 'SMALL_YACHT';
    }
    if(value == 'Big Yacht'){
      return 'BIG_YACHT';
    }
    if(value == 'Sailboat'){
      return 'SAILBOAT';
    }
    if(value == 'Brig'){
      return 'BRIG';
    }
    if(value == 'Cabin Cruiser'){
      return 'CABIN_CRUISER';
    }
    if(value == 'Cruise'){
      return 'CRUISE';
    }
    return '';
  }

}
