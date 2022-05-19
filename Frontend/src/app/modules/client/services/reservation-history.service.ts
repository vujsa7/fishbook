import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { ReservationHistory } from '../models/reservation-history.model';
import reservationHistory from 'src/assets/mocks/reservation-history-list.json';

@Injectable({
  providedIn: 'any'
})
export class ReservationHistoryService {

  private baseUrl: string = environment.baseUrl + "reservationHistory";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getReservationHistoryList(): Array<ReservationHistory> {
    return reservationHistory as Array<ReservationHistory>;
  }
}
