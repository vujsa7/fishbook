import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.scss']
})
export class PricingComponent implements OnInit {
  newBoatForm!: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.initializeForm();
  }
  
  private initializeForm() {
    this.newBoatForm = new FormGroup({
      price: new FormControl('', [Validators.required]),
      advancePayment: new FormControl('', [Validators.required])
    })
  }

}
