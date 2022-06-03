import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private baseUrl: string = environment.baseUrl + 'sellerReservations';

  constructor(private http: HttpClient, private authService: AuthService) { }

  postReservation(reservation: any): Observable<any> {
    return this.http.post(this.baseUrl, reservation, { headers : this.authService.getHeader() });
  }
}
