import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-primary-button',
  templateUrl: './primary-button.component.html',
  styleUrls: ['./primary-button.component.scss']
})
export class PrimaryButtonComponent {
  @Input() text: string = "Button";
  @Input() color: string = "#214975";
  @Input() textColor: string = "#214975";
  @Input() isBtnDisabled: boolean = false;
}
