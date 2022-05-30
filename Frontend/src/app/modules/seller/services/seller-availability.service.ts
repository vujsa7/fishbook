import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SellerAvailabilityService {
  private baseUrl: string = environment.baseUrl + 'sellerAvailabilities';

  constructor(private http: HttpClient, private authService: AuthService) {}

  postSellerAvailability(sellerAvailability: any): Observable<any> {
    return this.http.post(this.baseUrl, sellerAvailability, { headers : this.authService.getHeader() })
  }
}
