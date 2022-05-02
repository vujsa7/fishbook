import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-glass-container',
  templateUrl: './glass-container.component.html',
  styleUrls: ['./glass-container.component.scss']
})
export class GlassContainerComponent implements OnInit {

  @Input() borderRadius: number = 0;
  @Input() opacity: number = 1;

  constructor() { }

  ngOnInit(): void {
  }

}
