import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
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

  @Input() availableDates!: Array<DateRange>;
  @Input() unavailableDates!: Array<DateRange>;
  rangeSub!: Subscription;
  calendarMaxDate: Date = new Date(8640000000000000);
  maxDate: Date = this.calendarMaxDate;
  selectedStartingDate!: Date;

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

  ngOnChanges(changes: SimpleChanges) {
    this.changeMaxDate();
  }

  rangeFilter: DateFilterFn<Date> = (date: Date | null) => {
    if(date! <= new Date())
      return false;
    if(this.availableDates){
      for(let availableDateRange of this.availableDates){
        if(this.isDateInsideDateRange(date!, availableDateRange.start, availableDateRange.end)){
          if(this.unavailableDates.length > 0){
            for(let unavailableDateRange of this.unavailableDates){
              if(this.isDateInsideDateRange(date!, unavailableDateRange.start, unavailableDateRange.end))
                return false;
            }
            return true;
          }
          else
            return true;
        }
      }
      return false;
    }
    return true;
  };


  private isDateInsideDateRange(date: Date, dateRangeStart: string, dateRangeEnd: string) {
    let start = new Date(dateRangeStart)
    let end = new Date(dateRangeEnd)
    return date >= start && date <= end;
  }

  startDateChanged(e: any){
    this.selectedStartingDate = e.value;
    this.changeMaxDate();
  }

  changeMaxDate() {
    if (this.selectedStartingDate && this.availableDates) {
      for(let availableDateRange of this.availableDates){
        if(this.isDateInsideDateRange(this.selectedStartingDate, availableDateRange.start, availableDateRange.end)){
          if(this.unavailableDates.length > 0){
            for(let unavailableDateRange of this.unavailableDates)
              if(new Date(unavailableDateRange.start) > this.selectedStartingDate && this.isDateInsideDateRange(new Date(unavailableDateRange.start), availableDateRange.start, availableDateRange.end)){
                this.maxDate = new Date(unavailableDateRange.start);
                return;
              }    
          }
          this.maxDate = new Date(availableDateRange.end);
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
