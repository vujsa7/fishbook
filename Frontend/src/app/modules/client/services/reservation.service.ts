import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { Reservation } from '../models/reservation.model';
import { Observable } from 'rxjs';
import reservationHistory from 'src/assets/mocks/reservation-history-list.json';
import reservationOfferDetails from 'src/assets/mocks/reservation-offer-details.json';
import { ReservationOfferDetails } from '../models/reservation-offer-details.model';

@Injectable({
  providedIn: 'any'
})
export class ReservationService {
 

  private baseUrl: string = environment.baseUrl + "reservations";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getReservationHistoryList(): Array<Reservation> {
    // TODO: Fetch reservations from backend
    return reservationHistory as Array<Reservation>;
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
    // TODO: Implement reviewing entity
    return new Observable(subscriber => {
      subscriber.next("review left");
    })
  }

  getReservationOfferDetails(entityId: number): ReservationOfferDetails {
    // TODO: Implement getting reservation details
    return reservationOfferDetails as ReservationOfferDetails;
  }

  makeReservation() {
    // TODO: Implement making reservation
    return new Observable(subscriber => {
      subscriber.next("reservationMade");
    })
  }
  
  
}
