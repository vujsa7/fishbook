import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.scss']
})
export class PricingComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Input() entityType!: string;
  @Input() edit!: boolean;
  @Input() entityUpdateRequest!: any;
  @Output() addPricingEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
    this.initializeForm();
  }
  
  private initializeForm() {
    this.newEntityForm = new FormGroup({
      price: new FormControl('', [Validators.required]),
      advancePayment: new FormControl('', [Validators.required]),
      additionalServices: new FormArray([])
    })
  }

  additionalServices() : FormArray { 
    return this.newEntityForm.get("additionalServices") as FormArray
  }

  newAdditionalService() : FormGroup {
    return new FormGroup({
      name: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required])
    })
  }

  addAdditionalService() {
    if(this.newEntityForm.valid){
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
    if(this.newEntityForm.valid){
      if(this.entityType == "boat") {
        this.addBoatPricing();
      }
      if(this.entityType == "house") {
        this.addHousePricing();
      }
      if(this.entityType == "adventure"){
        this.addAdventurePricing();
      }
      this.addPricingEvent.emit();
    }
  }

  addBoatPricing() {
    this.entityRegistrationRequest.price = this.newEntityForm.controls.price.value;
    this.entityRegistrationRequest.advancePayment = this.newEntityForm.controls.advancePayment.value;
    this.entityRegistrationRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
  }

  addHousePricing() {
    this.entityRegistrationRequest.price = this.newEntityForm.controls.price.value;
    this.entityRegistrationRequest.cancellationFee = this.newEntityForm.controls.advancePayment.value;
    this.entityRegistrationRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
  }

  addAdventurePricing(){
    this.entityRegistrationRequest.pricePerDay = this.newEntityForm.controls.price.value;
    this.entityRegistrationRequest.cancellationFee = this.newEntityForm.controls.advancePayment.value;
    this.entityRegistrationRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
  }

}
