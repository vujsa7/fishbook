import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {
  dialogVisible: boolean = false;
  
  constructor() { }

  ngOnInit(): void {
  }

  showDialog(): void {
    this.dialogVisible = true;
  }

  hideDialog(): void {
    this.dialogVisible = false;
  }

}
