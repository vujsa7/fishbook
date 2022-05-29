import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-house-details',
  templateUrl: './house-details.component.html',
  styleUrls: ['./house-details.component.scss']
})
export class HouseDetailsComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Input() edit!: boolean;
  @Input() entityUpdateRequest!: any;
  @Output() addHouseDetailsEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
    this.initializeForm();
  }

  addAdventureDetails(){
    
    this.addHouseDetailsEvent.emit();
  }

  private initializeForm() {
    this.newEntityForm = new FormGroup({
      maxPeople : new FormControl('', [Validators.required]),
      rooms : new FormArray([])
    })
  }

  rooms() : FormArray {
    return this.newEntityForm.get("rooms") as FormArray
  }

  newRoom() : FormGroup {
    return new FormGroup ({
      numOfBeds: new FormControl('', [Validators.required])
    })
  }

  addRoom() {
    if(this.newEntityForm.valid){
      this.rooms().push(this.newRoom());
    }
  }

  onRoomsChanged(i:number) {
    if(this.rooms().at(i).value.numOfBeds == undefined) {
      this.removeRoom(i);
    }
  }

  removeRoom(i:number) {
    this.rooms().removeAt(i);
  }
  
  addHouseDetails(){
    if(this.edit){
      this.entityUpdateRequest.maxPeople = this.newEntityForm.controls.maxPeople.value;
      this.entityUpdateRequest.rooms = this.newEntityForm.controls.rooms.value;
    } else {
      this.entityRegistrationRequest.maxPeople = this.newEntityForm.controls.maxPeople.value;
      this.entityRegistrationRequest.rooms = this.newEntityForm.controls.rooms.value;
    }

    this.addHouseDetailsEvent.emit();
  }

}
