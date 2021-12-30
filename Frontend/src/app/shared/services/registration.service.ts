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
  private header = {
    headers: new HttpHeaders()
      .set('Authorization',  `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJmaXNoYm9vayIsInN1YiI6Im5pa2lAZ21haWwuY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjQwODk1MjkyLCJleHAiOjE2NDA4OTcwOTJ9.KmOm_c5VKbnFnwPse4pyq00ScUezz90GAnfWPVFivl57CXCwfOr1IQbAafWLPQFOKn6YJ34bGOrhZu1N4G9PXQ`)
  }

  constructor(private http: HttpClient) { }

  postRegistrationRequest(registrationRequest: RegistrationRequest): Observable<RegistrationRequest> {
    return this.http.post<RegistrationRequest>(this.baseUrl + 'registrationRequests', registrationRequest);
  }

  getRegistartionRequests(): Observable<RegistrationRequest[]> {
    return this.http.get<RegistrationRequest[]>(this.baseUrl + 'registrationRequests', this.header);
  }
}
