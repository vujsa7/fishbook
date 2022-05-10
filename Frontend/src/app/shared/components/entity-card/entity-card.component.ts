import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { EntityBasicInfo } from '../../models/entity-basic-info.model';

@Component({
  selector: 'app-entity-card',
  templateUrl: './entity-card.component.html',
  styleUrls: ['./entity-card.component.scss']
})
export class EntityCardComponent implements OnInit {

  @Input() entityBasicInfo!: EntityBasicInfo;
  role: string = 'ROLE_UNSIGNED';

  constructor(private authService: AuthService){}

  ngOnInit(): void {
    this.role = this.authService.getTokenRole();
  }
  
}
