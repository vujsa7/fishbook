import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.scss']
})
export class AdminHeaderComponent implements OnInit {
  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;
  selectedButton: string = 'business';

  constructor(private router: Router) {
    router.events.subscribe(
      data => {
        if(this.router.url.includes("password-renewal") || this.router.url.includes("login") || this.router.url.includes("register")){
          this.isVisible = false;
        }else {
          this.isVisible = true;
        }
      }
    );
  }

  ngOnInit(): void {
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
