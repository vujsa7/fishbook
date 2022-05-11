import { Component, Input } from '@angular/core';
import { MapLocation } from 'src/app/shared/models/map-location.model';

@Component({
  selector: 'adventure-rental-details',
  templateUrl: './rental-details.component.html',
  styleUrls: ['./rental-details.component.scss']
})
export class RentalDetailsComponent {

  @Input() price!: number;
  @Input() location!: MapLocation;
  @Input() maxPeople!: number;

}
