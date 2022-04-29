import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BoatService {
  private baseUrl: string = environment.baseUrl;
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.header = authService.getHeader();
   }

  postRegistrationRequest(boatRegistrationRequest: Object): Observable<any>{
    return this.http.post(this.baseUrl + 'boats', boatRegistrationRequest, { headers: this.header });
  }

  getBoatRules(): Observable<any> {
    return this.http.get(this.baseUrl + 'boats/rules', { headers: this.header });
  }

  getBoatEquipment(): Observable<any> {
    return this.http.get(this.baseUrl + 'boats/equipment', { headers: this.header });
  }
  
}
