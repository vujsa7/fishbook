import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  selectedButton: string = 'ROLE_CLIENT';
  users: any[] = [];

  constructor(private userService: UserService, private toastr: ToastrService) { }

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

  deleteUser(id: number){
    this.userService.deleteUser(id).subscribe(data => {
      this.users = this.users.filter(user => user.id != id);
    }, error => {
      this.toastr.error("Error deleting user", error);
    });
    
  }

}
