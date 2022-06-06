import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { SellerReport } from '../models/seller-report.model';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  

  private baseUrl: string = environment.baseUrl + "reports";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getSellerReports() : Observable<Array<SellerReport>>{
    return this.http.get<Array<SellerReport>>(this.baseUrl + '/seller', { headers : this.authService.getHeader() });
  }
  
  giveResponseOnSellerReport(response: any): Observable<any> {
    return this.http.post<any>(this.baseUrl + '/seller/response', response, { headers : this.authService.getHeader() });
  }
}
