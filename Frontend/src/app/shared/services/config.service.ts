import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalConfig } from 'src/app/models/config/global-config.model';
import { LoyaltyConfig } from 'src/app/models/config/loyalty-config.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private baseUrl: string = environment.baseUrl;
  // For testing 
  private header = new HttpHeaders()
      .set('Authorization',  `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJmaXNoYm9vayIsInN1YiI6Im5pa2lAZ21haWwuY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjQxMzk1MDMwLCJleHAiOjE2NDEzOTY4MzB9.w-SgCwQKRVNyLeaSP7gA5rsHhpCDjoCoA0skQBlQWieSomSoEXs_obUkIVDqSsQjAcBGggNmtCKOclMcjh-LWQ`)
      .set('Content-Type', 'application/json')
  
  constructor(private http: HttpClient) { }

  getLoyaltyConfig(): Observable<LoyaltyConfig[]> {
    return this.http.get<LoyaltyConfig[]>(this.baseUrl + 'config/loyalty', {headers: this.header});
  }

  getGlobalConfig(): Observable<GlobalConfig> {
    return this.http.get<GlobalConfig>(this.baseUrl + 'config/global', {headers: this.header});
  }

  updateLoyaltyConfig(loyaltyConfig: LoyaltyConfig): Observable<any> {
    return this.http.put<any>(this.baseUrl + 'config/loyalty/' + loyaltyConfig.id, loyaltyConfig, {headers: this.header});
  }

  updateGlobalConfig(globalConfig: GlobalConfig): Observable<any> {
    return this.http.put<any>(this.baseUrl + 'config/global/' + globalConfig.id, globalConfig, {headers: this.header});
  }
}
