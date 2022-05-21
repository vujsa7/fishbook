import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Equipment } from 'src/app/shared/models/equipment.model';
import { EquipmentService } from '../../../services/equipment.service';

@Component({
  selector: 'app-adventure-details',
  templateUrl: './adventure-details.component.html',
  styleUrls: ['./adventure-details.component.scss']
})
export class AdventureDetailsComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Input() edit!: boolean;
  @Input() entityUpdateRequest!: any;
  @Output() addAdventureDetailsEvent = new EventEmitter();
  equipment: Array<Equipment> = new Array();
  
  constructor(private equipmentService: EquipmentService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.equipmentService.getEquipment("fishingLesson").subscribe(
      data => {
        this.equipment = data;
        if(this.edit){
          this.initializeUpdateForm();
          const selectedEquipment = (this.newEntityForm.controls.equipment as FormArray);
          for(let e of this.entityUpdateRequest.equipment) {
            selectedEquipment.push(new FormControl(this.equipment.filter(eq => eq.name == e)[0]));
          }
        }
      }
    );
  }

  addAdventureDetails(){
    if(this.edit){
      this.entityUpdateRequest.instructorBiography = this.newEntityForm.controls.instructorBiography.value;
      this.entityUpdateRequest.maxNumberOfPeople = this.newEntityForm.controls.maxNumberOfPeople.value;
      this.entityUpdateRequest.equipment = this.newEntityForm.controls.equipment?.value;
    } else {
      this.entityRegistrationRequest.instructorBiography = this.newEntityForm.controls.instructorBiography.value;
      this.entityRegistrationRequest.maxNumberOfPeople = this.newEntityForm.controls.maxNumberOfPeople.value;
      this.entityRegistrationRequest.equipment = this.newEntityForm.controls.equipment?.value;
    }
    
    this.addAdventureDetailsEvent.emit();
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

  private initializeForm(): void{
    this.newEntityForm = new FormGroup({
      instructorBiography: new FormControl('', [Validators.required]),
      maxNumberOfPeople: new FormControl('', [Validators.required]),
      equipment : new FormArray([])
    });
  }

  private initializeUpdateForm(): void {
    this.newEntityForm.get('instructorBiography')?.setValue(this.entityUpdateRequest.instructorBiography);
    this.newEntityForm.get('maxNumberOfPeople')?.setValue(this.entityUpdateRequest.maxNumberOfPeople);
  }

}
