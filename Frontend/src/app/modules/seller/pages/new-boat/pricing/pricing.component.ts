import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { BoatRegistrationRequest } from '../../../models/boat-registration-request';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.scss']
})
export class PricingComponent implements OnInit {
  newBoatForm!: FormGroup;
  @Input() boatRegistrationRequest!: BoatRegistrationRequest;
  @Output() addPricingEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
    this.initializeForm();
  }
  
  private initializeForm() {
    this.newBoatForm = new FormGroup({
      price: new FormControl('', [Validators.required]),
      advancePayment: new FormControl('', [Validators.required]),
      additionalServices: new FormArray([])
    })
  }

  additionalServices() : FormArray { 
    return this.newBoatForm.get("additionalServices") as FormArray
  }

  newAdditionalService() : FormGroup {
    return new FormGroup({
      name: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required])
    })
  }

  addAdditionalService() {
    if(this.newBoatForm.valid){
      this.additionalServices().push(this.newAdditionalService()); 
    }
  }

  onAdditionalServiceChanged(i:number) {
    if(this.additionalServices().at(i).value.name == '' && this.additionalServices().at(i).value.price == null) {
      this.removeAdditionalService(i);
    }
  }

  removeAdditionalService(i:number) {  
    this.additionalServices().removeAt(i);  
  }

  addPricing() {
    if(this.newBoatForm.valid){
      this.boatRegistrationRequest.price = this.newBoatForm.controls.price.value;
      this.boatRegistrationRequest.advancePayment = this.newBoatForm.controls.advancePayment.value;
      this.boatRegistrationRequest.additionalServices = this.newBoatForm.controls.additionalServices.value;
      this.addPricingEvent.emit();
    }
  }

}
