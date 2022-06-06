import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeleteRequestService {
  baseUrl: string = environment.baseUrl + 'deleteRequests';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllRequests(): Observable<any> {
    return this.http.get(this.baseUrl, { headers : this.authService.getHeader() });
  }

  sendResponse(response: any): Observable<any> {
    return this.http.put(this.baseUrl, response, { headers : this.authService.getHeader() });
  }
}
