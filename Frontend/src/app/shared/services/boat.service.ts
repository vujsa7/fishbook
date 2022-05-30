import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { BoatDetails } from 'src/app/modules/public/entities/models/boat-details.model';
import { environment } from 'src/environments/environment';
import { EntityBasicInfo } from '../models/entity-basic-info.model';

@Injectable({
  providedIn: 'root'
})
export class BoatService {
  private baseUrl: string = environment.baseUrl + 'boats';

  constructor(private http: HttpClient, private authService: AuthService) {}

  postRegistrationRequest(boatRegistrationRequest: Object): Observable<any>{
    return this.http.post(this.baseUrl, boatRegistrationRequest, { headers: this.authService.getHeader() });
  }

  getAllBoats(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl);
  }

  getBoatsForOwner(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl + "?ownerUsername=" + this.authService.getTokenUsername());
  }

  public fetchBoatDetails(id: number): Observable<BoatDetails> {
    return this.http.get<BoatDetails>(this.baseUrl + "/" + id);
  }

  deleteBoat(id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + "/" + id, { headers: this.authService.getHeader() });
  }

  updateBoat(boat: any): Observable<any>{
    return this.http.put<any>(this.baseUrl + "/" + boat.id, boat, { headers: this.authService.getHeader() });
  }
}
