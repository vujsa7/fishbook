import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;

  constructor(private router: Router) {
    router.events.subscribe(
      data => {
        if(this.router.url.includes("login") || this.router.url.includes("register"))
          this.isVisible = false;
      }
    );
   }
  
  ngOnInit(): void {
    
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

}
