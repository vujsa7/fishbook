

import { Component, HostListener } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-base-header',
  templateUrl: './base-header.component.html',
  styleUrls: ['./base-header.component.scss']
})
export class BaseHeaderComponent{

  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;
  isSettingsVisible: boolean = false;
  isGlassEffect: boolean = false;
  _ = require('lodash');
  debouncedOnScroll = this._.debounce(() => this.toggleNavigationBackground(), 300, {})
  userRole: string = "ROLE_UNSIGNED";
  profileImageUrl!: string;
  fullName: string = "";

  constructor(private router: Router, private authService: AuthService, private userService: UserService) {
    this.authService.userEmailObservable.subscribe(_ => this.refreshHeaderInfo());
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes("login") || this.router.url.includes("register") || this.router.url.includes("password-renewal")){
          this.isVisible = false;
        } else {
          this.isVisible = true;
        }
      }
    });
  }

  refreshHeaderInfo() {
    if(this.authService.isAuthenticated()){
      this.userService.getUserProfilePhoto().subscribe(
        data => {
          this.profileImageUrl = data;
        },
        _ => {
          this.profileImageUrl = "";
        }
      )
      this.fullName = this.authService.getTokenFullName();
    }
    this.userRole = this.authService.getTokenRole();
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
    this.profileImageUrl = "";
    this.authService.flushToken();
    this.isSettingsVisible = false;
    this.router.navigate(['']);
  }
  

}


