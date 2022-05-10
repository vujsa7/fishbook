import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { EntityBasicInfo } from '../models/entity-basic-info.model';

@Injectable({
  providedIn: 'root'
})
export class BoatService {
  private baseUrl: string = environment.baseUrl + 'boats';
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.header = authService.getHeader();
   }

  postRegistrationRequest(boatRegistrationRequest: Object): Observable<any>{
    return this.http.post(this.baseUrl, boatRegistrationRequest, { headers: this.header });
  }

  getBoatRules(): Observable<any> {
    return this.http.get(this.baseUrl + '/rules', { headers: this.header });
  }

  getBoatEquipment(): Observable<any> {
    return this.http.get(this.baseUrl + '/equipment', { headers: this.header });
  }

  getAllBoats(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl);
  }

  deleteBoat(id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + "/" + id, { headers: this.authService.getHeader() });
  }
}
