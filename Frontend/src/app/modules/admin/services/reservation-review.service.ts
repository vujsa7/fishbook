import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationReviewService {
  baseUrl: string = environment.baseUrl + 'reviews';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<any> {
    return this.http.get(this.baseUrl, { headers : this.authService.getHeader() });
  }

  sendResponse(response: any): Observable<any> {
    return this.http.put(this.baseUrl, response, { headers : this.authService.getHeader() });
  }
}
