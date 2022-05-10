import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Equipment } from 'src/app/shared/models/equipment.model';
import { BoatService } from 'src/app/shared/services/boat.service';

@Component({
  selector: 'app-boat-spec',
  templateUrl: './boat-spec.component.html',
  styleUrls: ['./boat-spec.component.scss']
})
export class BoatSpecComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
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

  addBoatSpecification() {
    if(this.newEntityForm.valid){
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

      this.addBoatSpecificationEvent.emit();
    }
  }

}
