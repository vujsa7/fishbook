import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistrationRequest } from 'src/app/models/registration/registration-request.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  postRegistrationRequest(registrationRequest: RegistrationRequest): Observable<RegistrationRequest> {
    return this.http.post<RegistrationRequest>(this.baseUrl + 'registrationRequests', registrationRequest);
  }
}
