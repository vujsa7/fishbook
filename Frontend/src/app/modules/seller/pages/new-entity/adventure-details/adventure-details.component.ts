import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Equipment } from 'src/app/shared/models/equipment.model';
import { AdventureService } from 'src/app/shared/services/adventure.service';

@Component({
  selector: 'app-adventure-details',
  templateUrl: './adventure-details.component.html',
  styleUrls: ['./adventure-details.component.scss']
})
export class AdventureDetailsComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Output() addAdventureDetailsEvent = new EventEmitter();
  equipment: Array<Equipment> = new Array();
  
  constructor(private adventureService: AdventureService) { }

  ngOnInit(): void {
    this.initializeForm();
    this.adventureService.getAdventureFishingEquipemnt().subscribe(
      data => {
        this.equipment = data;
      }
    );
  }

  addAdventureDetails(){
    this.entityRegistrationRequest.instructorBiography = this.newEntityForm.controls.instructorBiography.value;
    this.entityRegistrationRequest.maxNumberOfPeople = this.newEntityForm.controls.maxNumberOfPeople.value;
    this.entityRegistrationRequest.equipment = this.newEntityForm.controls.equipment?.value;
    
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

}
