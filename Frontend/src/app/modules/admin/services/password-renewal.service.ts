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
    let options = {params: new HttpParams().set('username', username), headers: this.header}
    return this.http.get<any>(this.baseUrl + 'passwordRenewalMarks', options);
  }

  deletePasswordRenewalMark(username: string): Observable<any> {
    return this.http.delete<any>(this.baseUrl + 'passwordRenewalMarks/' + username, {headers: this.header});
  }

  renewPassword(): void {
    //this.http.put<any>(this.baseUrl + '/users', )
  }
}
