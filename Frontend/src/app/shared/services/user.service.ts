import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  
  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUser() : Observable<any> {
    return this.http.get<any>(this.baseUrl + 'users/' + this.authService.getTokenUsername(), { headers: this.authService.getHeader()});
  }

  updateUser(user: any) : Observable<any> {
    return this.http.put<any>(this.baseUrl + 'users/' + this.authService.getTokenUsername(), user, { headers: this.authService.getHeader()});
  }

  updatePassword(newPassword: any) : Observable<any> {
    return this.http.put<any>(this.baseUrl + 'users/' + this.authService.getTokenUsername() + '/password', newPassword, { headers: this.authService.getHeader()});
  }

  updateProfilePhoto(image: FormData) : Observable<string> {
    return this.http.put<any>(this.baseUrl + 'users/' + this.authService.getTokenUsername() + '/profileImage', image, { headers: this.authService.getFormHeader()});
  }

  getUserProfilePhoto(): Observable<string> {
    return this.http.get(this.baseUrl + 'users/' + this.authService.getTokenUsername() + '/profileImage', { headers: this.authService.getHeader(), responseType: "text"});
  }

  postDeleteAccountRequest(requestMessage: string): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'users/' + this.authService.getTokenUsername() + '/deleteAccountRequest', requestMessage, { headers: this.authService.getHeader()});
  }
}
