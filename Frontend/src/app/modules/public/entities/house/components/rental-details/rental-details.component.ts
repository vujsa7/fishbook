import { Component, Input } from '@angular/core';
import { MapLocation } from 'src/app/shared/models/map-location.model';
import { HouseSpecifications } from '../../../models/house-specifications.model';

@Component({
  selector: 'house-rental-details',
  templateUrl: './rental-details.component.html',
  styleUrls: ['./rental-details.component.scss']
})
export class RentalDetailsComponent{

  @Input() houseSpecifications!: HouseSpecifications;
  @Input() price!: number;
  @Input() location!: MapLocation;

}
