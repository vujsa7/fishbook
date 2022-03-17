import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {
  dialogVisible: boolean = false;
  selectedButton: string = 'registration-requests';
  
  constructor() { }

  ngOnInit(): void {
  }

  showDialog(): void {
    this.dialogVisible = true;
  }

  hideDialog(): void {
    this.dialogVisible = false;
  }
  
  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
