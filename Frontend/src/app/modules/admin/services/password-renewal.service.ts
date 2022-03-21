import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from 'src/app/shared/services/auth.service';
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
    this.header = this.authService.getHeader();
    let options = {params: new HttpParams().set('username', username), headers: this.header}
    return this.http.get<any>(this.baseUrl + 'passwordRenewalMarks', options);
  }

  renewPassword(newPassword: any): Observable<any> {
    let options: any = {
      headers: this.authService.getHeader(), 
      responseType: 'text'
    }
    return this.http.put<any>(this.baseUrl + 'passwordRenewalMarks/' + this.authService.getTokenUsername(), newPassword, options);
  }
}
