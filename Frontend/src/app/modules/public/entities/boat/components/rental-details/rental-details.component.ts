import { Component, Input } from '@angular/core';
import { MapLocation } from 'src/app/shared/models/map-location.model';
import { BoatSpecifications } from '../../../models/boat-specifications.model';

@Component({
  selector: 'boat-rental-details',
  templateUrl: './rental-details.component.html',
  styleUrls: ['./rental-details.component.scss']
})
export class RentalDetailsComponent {

  @Input() boatSpecifications!: BoatSpecifications;
  @Input() price!: number;
  @Input() location!: MapLocation;

}
