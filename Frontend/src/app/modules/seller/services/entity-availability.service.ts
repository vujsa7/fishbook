import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EntityAvailabilityService {
  private baseUrl: string = environment.baseUrl + 'entityAvailabilities';

  constructor(private http: HttpClient, private authService: AuthService) {}

  postEntityAvailability(entityAvailability: any): Observable<any> {
    return this.http.post(this.baseUrl, entityAvailability, { headers : this.authService.getHeader() })
  }
}
