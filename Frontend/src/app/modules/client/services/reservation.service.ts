import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { Reservation } from '../models/reservation.model';
import { Observable } from 'rxjs';
import reservationHistory from 'src/assets/mocks/reservation-history-list.json';
import reservationOfferDetails from 'src/assets/mocks/reservation-offer-details.json';
import { ReservationOfferDetails } from '../models/reservation-offer-details.model';
import { DateRange } from 'src/app/shared/models/date-range.model';
import { SpecialOfferDetails } from '../models/special-offer-details.model';

@Injectable({
  providedIn: 'any'
})
export class ReservationService {

  private baseUrl: string = environment.baseUrl + "clientReservations";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getReservationHistoryList(): Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(this.baseUrl + "/history", { headers: this.authService.getHeader() })
  }

  cancelReservation(reservationId: number): Observable<boolean> {
    return this.http.delete<any>(this.baseUrl + '/' + reservationId, { headers: this.authService.getHeader() })
  }

  reportSeller(report: { message: string, reservationId: number} ): Observable<any> {
    return this.http.post<any>(environment.baseUrl + 'reports/buyers', report, { headers: this.authService.getHeader() })
  }

  leaveReview(reservationId: number, review: { rating: number; comment: string; }): Observable<any> {
    return this.http.post<any>(this.baseUrl + '/' + reservationId + '/review', review, { headers: this.authService.getHeader() })
  }

  fetchDiscountAndFees(entityId: number): Observable<any> {
    return this.http.get<any>(environment.baseUrl + 'config/discountAndFees/' + entityId, { headers: this.authService.getHeader()})
  }

  getReservationOfferDetails(entityId: number): Observable<ReservationOfferDetails> {
    return this.http.get<ReservationOfferDetails>(this.baseUrl + '/details/' + entityId, { headers: this.authService.getHeader() });
  }

  makeReservation(reservation: any) {
    return this.http.post<ReservationOfferDetails>(this.baseUrl, reservation, { headers: this.authService.getHeader() });
  }

  getBoatOwnerUnavailability(entityId: number): Observable<Array<DateRange>> {
    return this.http.get<Array<DateRange>>(environment.baseUrl + 'sellerUnavailability/' + entityId, { headers: this.authService.getHeader() });
  }

  getSpecialOfferDetails(specialOfferId: any): Observable<SpecialOfferDetails> {
    return this.http.get<SpecialOfferDetails>(environment.baseUrl + 'specialOffers/' + specialOfferId, { headers: this.authService.getHeader() });
  }

  makeReservationOnSpecialOffer(specialOfferId: any) {
    return this.http.post<SpecialOfferDetails>(this.baseUrl + "/specialOffers/" + specialOfferId, null, { headers: this.authService.getHeader() });
  }


}
