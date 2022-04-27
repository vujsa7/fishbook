import { Component, Input } from '@angular/core';
import { EntityBasicInfo } from '../../models/entity-basic-info.model';

@Component({
  selector: 'app-entity-card',
  templateUrl: './entity-card.component.html',
  styleUrls: ['./entity-card.component.scss']
})
export class EntityCardComponent {

  @Input() entityBasicInfo!: EntityBasicInfo;
  
}
