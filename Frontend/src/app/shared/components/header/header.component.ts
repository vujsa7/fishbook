import { Component, OnInit } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent  implements OnInit{
  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;

  constructor(private router: Router) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes("login") || this.router.url.includes("register")){
          this.isVisible = false;
        } else {
          this.isVisible = true;
        }
      }
    });
   }
  ngOnInit(): void {
    console.log('inited with isVisible = ' + this.isVisible);
    
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

}
