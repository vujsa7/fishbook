import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {
  selectedButton: string = 'boats';

  constructor() { }

  ngOnInit(): void {
  }
  
  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
