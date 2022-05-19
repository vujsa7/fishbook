import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import stats from 'src/assets/mocks/stats-info.json';
import { StatsInfo } from '../models/stats-info.model';

@Injectable({
  providedIn: 'any'
})
export class StatsService {

  private baseUrl: string = environment.baseUrl + "reservationHistory";

  constructor(private http: HttpClient, private authService: AuthService) { }

  fetchStatsInfo(): StatsInfo {
    return stats as StatsInfo;
  }

  
}
