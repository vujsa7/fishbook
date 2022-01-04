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
      .set('Authorization',  `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJmaXNoYm9vayIsInN1YiI6Im5pa2lAZ21haWwuY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjQxMjk2NTU2LCJleHAiOjE2NDEyOTgzNTZ9.wM_jzoFAaL1eYYdWnOfr0uApzcqS223eRzV_Cms2VmCoqhdynCfGLfVNu2r8a1jZczuY8V9n4uTjA-ZvOe9-3w`)
      .set('Content-Type', 'application/json')
  

  constructor(private http: HttpClient) { }

  postRegistrationRequest(registrationRequest: RegistrationRequest): Observable<RegistrationRequest> {
    return this.http.post<RegistrationRequest>(this.baseUrl + 'registrationRequests', registrationRequest);
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
