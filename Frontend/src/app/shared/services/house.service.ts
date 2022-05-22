import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { EntityBasicInfo } from '../models/entity-basic-info.model';

@Injectable({
  providedIn: 'root'
})
export class HouseService {
  private baseUrl: string = environment.baseUrl + 'houses';
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.header = authService.getHeader();
   }

  postRegistrationRequest(houseRegistrationRequest: Object): Observable<any>{
    return this.http.post(this.baseUrl, houseRegistrationRequest, { headers: this.header });
  }

  getAllHouses(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl);
  }

  getHousesForOwner(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl + "?ownerUsername=" + this.authService.getTokenUsername());
  }

  deleteHouse(id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + "/" + id, { headers: this.authService.getHeader() });
  }
}
