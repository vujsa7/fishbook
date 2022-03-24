import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './public-header.component.html',
  styleUrls: ['./public-header.component.scss']
})
export class PublicHeaderComponent{

  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;
  isGlassEffect: boolean = false;
  _ = require('lodash');
  debouncedOnScroll = this._.debounce(() => this.toggleNavigationBackground(), 300, {})

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

  @HostListener('window:scroll', ['$event'])
  onScroll() {
    this.debouncedOnScroll();
  }

  toggleDropdownMenu(): void{
    this.dropdownMenuVisible = !this.dropdownMenuVisible;
  }

  toggleNavigationBackground(): void{
    if(window.pageYOffset == 0)
      this.isGlassEffect = false;
    else
      this.isGlassEffect = true;

    console.log(this.isGlassEffect)
  }
  

}

