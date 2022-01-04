import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-loyalty-settings',
  templateUrl: './loyalty-settings.component.html',
  styleUrls: ['./loyalty-settings.component.scss']
})
export class LoyaltySettingsComponent{
  @Input() title: string = '';
  @Input() loyaltyText: string = '';

  constructor() { }

}
