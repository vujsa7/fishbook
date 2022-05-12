import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'filter-price-slider',
  templateUrl: './price-slider.component.html',
  styleUrls: ['./price-slider.component.scss']
})
export class PriceSliderComponent {

  @Input() maxPrice!: number;
  @Output() selectedPriceChanged = new EventEmitter<number>();
  value: number = 0;
 
  
  selectPrice(){
    this.selectedPriceChanged.emit(this.value);
  }

  formatLabel(value: number): string {
    return "$" + value;
  }
}
