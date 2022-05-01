import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { EntityBasicInfo } from '../../models/entity-basic-info.model';

@Component({
  selector: 'app-entity-card',
  templateUrl: './entity-card.component.html',
  styleUrls: ['./entity-card.component.scss']
})
export class EntityCardComponent implements OnInit {

  @Input() entityType!: string;
  @Input() entityBasicInfo!: EntityBasicInfo;
  @Output() deleteEvent = new EventEmitter<number>();
  
  role: string = 'ROLE_UNSIGNED';

  constructor(private authService: AuthService){}

  ngOnInit(): void {
    this.role = this.authService.getTokenRole();
  }

  deleteEntity(value: number){
    this.deleteEvent.emit(value);
  }

  
}
