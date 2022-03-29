import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl: string = environment.baseUrl; 
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) { 
    this.header = authService.getHeader();
  }

  postAdmin(admin: any): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'users/admins', admin, {headers: this.header});
  }
}