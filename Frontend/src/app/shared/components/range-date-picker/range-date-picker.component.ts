import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DateFilterFn } from '@angular/material/datepicker';
import { Subscription } from 'rxjs';
import { DateRange } from '../../models/date-range.model';

@Component({
  selector: 'app-range-date-picker',
  templateUrl: './range-date-picker.component.html',
  styleUrls: ['./range-date-picker.component.scss']
})
export class RangeDatePickerComponent implements OnInit, OnDestroy {

  @Input() availableDates?: Array<DateRange>;
  rangeSub!: Subscription;
  calendarMaxDate: Date = new Date(8640000000000000);
  maxDate: Date = this.calendarMaxDate;

  @Output() rangeChanged = new EventEmitter<Object>(); 

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });

  ngOnInit(): void {
    this.rangeSub = this.range.valueChanges.subscribe(_ => {
      this.rangeChanged.emit({start: this.range.get("start")?.value, end: this.range.get("end")?.value})
    });
  }

  rangeFilter: DateFilterFn<Date> = (date: Date | null) => {
    if(this.availableDates){
      for(let dateRange of this.availableDates){
        let start = new Date(dateRange.start)
        start.setHours(0, 0, 0, 0)
        let end = new Date(dateRange.end)
        end.setHours(0, 0, 0, 0)
        if(date! >= start && date! <= end)
          return true;
      }
      return false;
    }
    return true;
  };

  startDateChanged(e: any){
    const date = e.value;
    if (date && this.availableDates) {
      for(let dateRange of this.availableDates){
        let start = new Date(dateRange.start)
        start.setHours(0, 0, 0, 0)
        let end = new Date(dateRange.end)
        end.setHours(0, 0, 0, 0)
        if(date! >= start && date! <= end){
          this.maxDate = end;
          return;
        }
      }
    } else {
      this.maxDate = this.calendarMaxDate;
    }
  }

  clearDates() {
    this.range.controls.start.setValue(undefined); 
    this.range.controls.end.setValue(undefined);
    this.maxDate = this.calendarMaxDate;
  }
  
  ngOnDestroy(){
    this.rangeSub.unsubscribe();
  }

  

}
