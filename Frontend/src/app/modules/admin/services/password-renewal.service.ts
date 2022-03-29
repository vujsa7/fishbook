import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PasswordRenewalService {
  private baseUrl: string = environment.baseUrl; 
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) { 
    this.header = authService.getHeader();
  }

  getPasswordRenewalMark(username: string): Observable<any> {
    let options = {
      headers: this.authService.getHeader(), 
      params: new HttpParams().set('username', username), 
      responseType: 'text' as 'text'
    }
    return this.http.get(this.baseUrl + 'passwordRenewalMarks', options);
  }

  renewPassword(newPassword: any): Observable<any> {
    let options: any = {
      headers: this.authService.getHeader(), 
      responseType: 'text'
    }
    return this.http.put<any>(this.baseUrl + 'passwordRenewalMarks/' + this.authService.getTokenUsername(), newPassword, options);
  }
}
