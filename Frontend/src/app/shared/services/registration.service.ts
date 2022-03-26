import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistrationRequest } from 'src/app/models/registration/registration-request.model';
import { environment } from 'src/environments/environment';
import { AuthService } from 'src/app/core/authentication/auth.service';;

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private baseUrl: string = environment.baseUrl;
  private header: HttpHeaders;
  
  constructor(private http: HttpClient, private authService: AuthService) {
    this.header = authService.getHeader();
   }

  postClient(client: Object): Observable<any>{
      return this.http.post<any>(this.baseUrl + 'users', client);
  }

  postRegistrationRequest(seller: Object): Observable<RegistrationRequest> {
    return this.http.post<RegistrationRequest>(this.baseUrl + 'registrationRequests', seller);
  }

  getRegistartionRequests(): Observable<RegistrationRequest[]> {
    return this.http.get<RegistrationRequest[]>(this.baseUrl + 'registrationRequests', {headers: this.header});
  }

  deleteRegistrationRequest(id: number, registrationResponse: any): Observable<any> {
    const options = {
      headers: this.header,
      body: registrationResponse    
    };

    return this.http.delete<any>(this.baseUrl + 'registrationRequests/' + id, options);
  }
}
