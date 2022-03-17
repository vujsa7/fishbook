import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-boat',
  templateUrl: './new-boat.component.html',
  styleUrls: ['./new-boat.component.scss']
})
export class NewBoatComponent implements OnInit {
  selectedButton: string = 'general';

  constructor() { }

  ngOnInit(): void {
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
