import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistrationRequest } from 'src/app/models/registration/registration-request.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private baseUrl: string = environment.baseUrl;
  // For testing 
  private header = new HttpHeaders()
      .set('Authorization',  `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJmaXNoYm9vayIsInN1YiI6Im5pa2lAZ21haWwuY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjQxMzA2MjI4LCJleHAiOjE2NDEzMDgwMjh9.YeOpO8n6XIXjINTXOVbPAjjzJQxWVpki8Dgl8zI-rTYzeOd27aqEFjgAhDXB0HbwPpn28Du8CBs65NE8Snkzfw`)
      .set('Content-Type', 'application/json')
  
  constructor(private http: HttpClient) { }

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
