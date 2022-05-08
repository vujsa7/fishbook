import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdventureService {
  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  postAdventure(adventure: any): Observable<any> {
    return this.http.post(this.baseUrl + "fishingLessons", adventure, { headers: this.authService.getHeader() });
  }

  getAdventureRules(): Observable<any> {
    return this.http.get(this.baseUrl + "fishingLessons/rules", { headers: this.authService.getHeader() });
  }

  getAdventureFishingEquipemnt(): Observable<any> {
    return this.http.get(this.baseUrl + "fishingLessons/equipment", { headers: this.authService.getHeader() });
  }
}
