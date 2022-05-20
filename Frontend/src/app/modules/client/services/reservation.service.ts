import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { ReservationHistory } from '../models/reservation-history.model';
import reservationHistory from 'src/assets/mocks/reservation-history-list.json';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'any'
})
export class ReservationService {
  
  private baseUrl: string = environment.baseUrl + "reservationHistory";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getReservationHistoryList(): Array<ReservationHistory> {
    return reservationHistory as Array<ReservationHistory>;
  }

  cancelReservation(reservationId: number): Observable<boolean> {
    // TODO: Implement canceling reservation
    return new Observable(subscriber => {
      subscriber.next(true);
    })
  }

  reportSeller(value: any) : Observable<any>{
    // TODO: Implement reporting seller
    return new Observable(subscriber => {
      subscriber.next("reported");
    })
  }

  leaveReview(review: { stars: number; review: string; }) : Observable<any> {
    return new Observable(subscriber => {
      subscriber.next("review left");
    })
  }
  
}
