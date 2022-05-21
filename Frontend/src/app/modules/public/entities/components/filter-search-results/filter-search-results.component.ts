import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'entities-filter-search-results',
  templateUrl: './filter-search-results.component.html',
  styleUrls: ['./filter-search-results.component.scss']
})
export class FilterSearchResultsComponent {

  @Input() maxPrice?: number;
  @Input() cities!: Array<any>;
  @Output() filterSelectionChanged = new EventEmitter<Object>();
  stars = new Array<number>(5, 4, 3, 2, 1);
  selectedPrice!: number;

  citiesForm: FormGroup;
  starsForm: FormGroup;

  constructor() {
    this.citiesForm = new FormGroup({
      cities: new FormArray([]),
    });
    this.starsForm = new FormGroup({
      stars: new FormArray([]),
    });
    this.stars.forEach(() => this.starsFormArray.push(new FormControl(false)));
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.cities)
      changes.cities.currentValue.forEach(() => this.citiesFormArray.push(new FormControl(false)));
  }

  get citiesFormArray() {
    return this.citiesForm.controls.cities as FormArray;
  }

  get starsFormArray(){
    return this.starsForm.controls.stars as FormArray;
  }

  onSelectedPriceChanged(price: number){
    this.selectedPrice = price;
    this.selectedFilterChanged();
  }

  selectedFilterChanged() {
    this.filterSelectionChanged.emit(
      {
        selectedCities: this.citiesForm.value.cities
          .map((checked: boolean, i: number) => checked ? this.cities[i] : null)
          .filter((v: any) => v !== null),
        selectedStars: this.starsForm.value.stars
          .map((checked: boolean, i: number) => checked ? this.stars[i] : null)
          .filter((v: any) => v !== null),
        selectedPrice: this.selectedPrice
      }
    )
  }
}
