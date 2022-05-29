import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AdditionalService } from 'src/app/shared/models/additional-service.model';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.scss']
})
export class PricingComponent implements OnInit {
  newEntityForm!: FormGroup;
  skipperService: any = {name: "Skipper", price: -1}
  @Input() entityRegistrationRequest!: any;
  @Input() entityType!: string;
  @Input() edit!: boolean;
  @Input() entityUpdateRequest!: any;
  @Output() addPricingEvent = new EventEmitter();

  constructor(private toastr: ToastrService) { }

  ngOnInit(): void {
    this.initializeForm();

    if(this.edit){
      this.initializeUpdateForm();
    }
  }
  
  private initializeForm() {
    this.newEntityForm = new FormGroup({
      price: new FormControl('', [Validators.required]),
      cancellationFee: new FormControl('', [Validators.required]),
      skipperService: new FormControl(''),
      skipperPrice: new FormControl(''),
      additionalServices: new FormArray([])
    })
  }

  private initializeUpdateForm(): void {
    this.newEntityForm.get('price')?.setValue(this.entityUpdateRequest.pricePerDay);
    this.newEntityForm.get('cancellationFee')?.setValue(this.entityUpdateRequest.cancellationFee);
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
      if(this.entityType == "boat" || this.entityType == "house") {
        if(this.newEntityForm.controls.skipperService.value && this.newEntityForm.controls.skipperPrice.value == undefined){
          this.toastr.error("Must specify price of skipper service", "Error");
          return;
        } else {
          this.addEntityPricing();
        }
      }
      if(this.entityType == "adventure"){
        this.addAdventurePricing();
      }
      this.addPricingEvent.emit();
    }
  }

  addEntityPricing() {
    if(this.edit){
      this.entityUpdateRequest.price = this.newEntityForm.controls.price.value;
      this.entityUpdateRequest.cancellationFee = this.newEntityForm.controls.cancellationFee.value;
      this.entityUpdateRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
      if(this.newEntityForm.controls.skipperService.value){
        this.skipperService.price = this.newEntityForm.controls.skipperPrice.value;
        this.entityRegistrationRequest.additionalServices.push(this.skipperService);
      }
    } else{
      this.entityRegistrationRequest.price = this.newEntityForm.controls.price.value;
      this.entityRegistrationRequest.cancellationFee = this.newEntityForm.controls.cancellationFee.value;
      this.entityRegistrationRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
      if(this.newEntityForm.controls.skipperService.value){
        this.skipperService.price = this.newEntityForm.controls.skipperPrice.value;
        this.entityRegistrationRequest.additionalServices.push(this.skipperService);
      }
    }
  }

  addAdventurePricing(){
    if(this.edit){
      this.entityUpdateRequest.pricePerDay = this.newEntityForm.controls.price.value;
      this.entityUpdateRequest.cancellationFee = this.newEntityForm.controls.cancellationFee.value;
      this.entityUpdateRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
    } else{
      this.entityRegistrationRequest.pricePerDay = this.newEntityForm.controls.price.value;
      this.entityRegistrationRequest.cancellationFee = this.newEntityForm.controls.cancellationFee.value;
      this.entityRegistrationRequest.additionalServices = this.newEntityForm.controls.additionalServices.value;
    }
  }

}
