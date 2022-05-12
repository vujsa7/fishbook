import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'entities-rental-details',
  templateUrl: './rental-details.component.html',
  styleUrls: ['./rental-details.component.scss']
})
export class RentalDetailsComponent implements OnInit {

  rentalDetailsForm!: FormGroup;
  @Output() searchByRentalDetails = new EventEmitter<Object>();
  range!: any;

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.rentalDetailsForm = new FormGroup({
      location: new FormControl(''),
      date: new FormControl(''),
      name: new FormControl(''),
    });
  }

  onRangeChanged(range: any){
    this.range = range;
  }

  onSubmit(): void {
    this.searchByRentalDetails.emit({
      location: this.rentalDetailsForm.get("location")?.value,
      date: this.rentalDetailsForm.get("date")?.value,
      name: this.rentalDetailsForm.get("name")?.value,
      range: this.range
    });
  }

}
