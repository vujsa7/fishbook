import { Component, OnInit } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  
  isVisible: boolean = true;

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
  }

}
