import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
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

  getAdventureRules(): Observable<any> {
    return this.http.get(this.baseUrl + "/rules", { headers: this.authService.getHeader() });
  }

  getAdventureFishingEquipemnt(): Observable<any> {
    return this.http.get(this.baseUrl + "/equipment", { headers: this.authService.getHeader() });
  }

  getAllAdventures(): Observable<EntityBasicInfo[]> {
    return this.http.get<EntityBasicInfo[]>(this.baseUrl);
  }
}
