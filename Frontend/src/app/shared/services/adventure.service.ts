import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { AdventureDetails } from 'src/app/modules/public/entities/models/adventure.details.model';
import { environment } from 'src/environments/environment';
import { EntityBasicInfo } from '../models/entity-basic-info.model';

@Injectable({
  providedIn: 'root'
})
export class AdventureService {
  private baseUrl: string = environment.baseUrl  + "fishingLessons";

  constructor(private http: HttpClient, private authService: AuthService) { }

  postAdventure(adventure: any): Observable<any> {
    return this.http.post(this.baseUrl, adventure, { headers: this.authService.getHeader() });
  }

  getAllAdventures(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl);
  }

  getAdventuresForOwner(): Observable<EntityBasicInfo[]> {
    let params = new HttpParams().set('ownerUsername', this.authService.getTokenUsername());
    return this.http.get<EntityBasicInfo[]>(this.baseUrl, { params: params });
  }

  public fetchAdventureDetails(id: number): Observable<AdventureDetails>{
    return this.http.get<AdventureDetails>(this.baseUrl + "/" + id);
  }

  deleteAdventure(id: number): Observable<any>{
    return this.http.delete<any>(this.baseUrl + "/" + id, { headers: this.authService.getHeader() });
  }

  updateAdventure(adventure: any): Observable<any>{
    return this.http.put<any>(this.baseUrl + "/" + adventure.id, adventure, { headers: this.authService.getHeader() });
  }
}
