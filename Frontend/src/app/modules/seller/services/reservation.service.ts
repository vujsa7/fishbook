import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  postReservation(reservation: any): Observable<any> {
    return this.http.post(this.baseUrl + 'sellerReservations', reservation, { headers : this.authService.getHeader() });
  }

  getReservations(entityId: number): Observable<any> {
    let options = {
      params: new HttpParams().set('entityId', entityId),
      headers: this.authService.getHeader()
    }
    return this.http.get(this.baseUrl + 'reservations', options);
  }

  calculateRevenue(request: any): Observable<any> {
    let options = {
      params: new HttpParams()
          .set('entityId', request.entityId)
          .set('startDate', request.startDate)
          .set('endDate', request.endDate),
      headers: this.authService.getHeader()
    }
    return this.http.get(this.baseUrl + 'reservations/revenue', options);
  }
}
