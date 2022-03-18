import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.scss']
})
export class AdminHeaderComponent implements OnInit {
  dropdownMenuVisible: boolean = false;
  selectedButton: string = 'business';

  constructor() { }

  ngOnInit(): void {
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
