import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.scss']
})
export class AdminHeaderComponent implements OnInit {
  isVisible: boolean = true;
  dropdownMenuVisible: boolean = false;
  selectedButton: string = 'business';
  adminName: string = '';
  isSettingsVisible: boolean = false;
  isGlassEffect: boolean = false;
  _ = require('lodash');
  debouncedOnScroll = this._.debounce(() => this.toggleNavigationBackground(), 300, {})

  constructor(private router: Router, private authService: AuthService) {
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
    this.adminName = this.authService.getTokenUsername();
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
    this.router.navigate(['/home']);
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
  }

}
