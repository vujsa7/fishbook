import { Component, Input } from '@angular/core';
import { BoatSpecifications } from '../../../models/boat-specifications.model';

@Component({
  selector: 'boat-specifications',
  templateUrl: './specifications.component.html',
  styleUrls: ['./specifications.component.scss']
})
export class SpecificationsComponent {

  @Input() boatSpecifications!: BoatSpecifications;
  @Input() navigationEquipment!: Array<string>;
  @Input() fishingEquipment!: Array<string>;

}
