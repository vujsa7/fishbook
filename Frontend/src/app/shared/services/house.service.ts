import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HouseService {
  private baseUrl: string = environment.baseUrl;
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.header = authService.getHeader();
   }

  postRegistrationRequest(houseRegistrationRequest: Object): Observable<any>{
    return this.http.post(this.baseUrl + 'houses', houseRegistrationRequest, { headers: this.header });
  }

  getHouseRules(): Observable<any> {
    return this.http.get(this.baseUrl + 'houses/rules', { headers: this.header });
  }
}
