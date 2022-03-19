import { Component, OnInit } from '@angular/core';
import { Router, Event, NavigationStart } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  role: string = 'ROLE_UNSIGNED';

  constructor(private authService: AuthService, private router: Router){
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationStart) {
        this.role = this.authService.getTokenRole();
      }
    });
  }

  ngOnInit() {
    if(this.role == 'undefined')
      this.role = this.authService.getTokenRole();
  }

  flushToken() {
    this.authService.setToken('');
  }


}
