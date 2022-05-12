import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-range-date-picker',
  templateUrl: './range-date-picker.component.html',
  styleUrls: ['./range-date-picker.component.scss']
})
export class RangeDatePickerComponent implements OnInit, OnDestroy {

  rangeSub!: Subscription;
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

  ngOnDestroy(){
    this.rangeSub.unsubscribe();
  }

  

}
