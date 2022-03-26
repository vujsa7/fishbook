

import { Component, HostListener } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';


@Component({
  selector: 'app-client-header',
  templateUrl: './client-header.component.html',
  styleUrls: ['./client-header.component.scss']
})
export class ClientHeaderComponent{

  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;
  isSettingsVisible: boolean = false;
  isGlassEffect: boolean = false;
  _ = require('lodash');
  debouncedOnScroll = this._.debounce(() => this.toggleNavigationBackground(), 300, {})

  constructor(private router: Router, private authService: AuthService) {
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

  @HostListener('window:scroll', ['$event'])
  onScroll() {
    this.debouncedOnScroll();
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

  toggleSettings(): void{
    this.isSettingsVisible = !this.isSettingsVisible;
  }

  toggleNavigationBackground(): void{
    if(window.pageYOffset == 0)
      this.isGlassEffect = false;
    else
      this.isGlassEffect = true;
  }

  logout(): void{
    this.authService.setToken("");
    window.location.reload();
  }
  

}


