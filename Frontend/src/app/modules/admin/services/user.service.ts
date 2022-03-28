import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from 'src/app/shared/services/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUsersByRole(role: string): Observable<any> {
    let options = {
      headers: this.authService.getHeader(), 
      params: new HttpParams().set('role', role)
    }
    return this.http.get<any>(this.baseUrl + 'users', options);
  }
}
