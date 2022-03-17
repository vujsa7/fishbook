import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  selectedButton: string = 'seller-reports';

  constructor() { }

  ngOnInit(): void {
  }
  
  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
