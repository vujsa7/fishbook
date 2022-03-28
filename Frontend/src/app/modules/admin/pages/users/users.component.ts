import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  selectedButton: string = 'ROLE_CLIENT';
  users: any[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getUsersByRole('ROLE_CLIENT');
  }

  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
    this.getUsersByRole(selectedButton);
  }

  getUsersByRole(role: string) {
    this.userService.getUsersByRole(role).subscribe(
      data => {
        this.users = data;
      }
    );
  }

  deleteUser(username: string){
    this.userService.deleteUser(username).subscribe();
    this.users.map(user => {
      if(user.email == username){
        user.deleted = true;
        user.enabled = false;
        return user;
      }
    });
  }

}
