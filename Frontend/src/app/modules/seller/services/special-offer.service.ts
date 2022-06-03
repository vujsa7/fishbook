import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SpecialOfferService {

  private baseUrl: string = environment.baseUrl + 'specialOffers';

  constructor(private http: HttpClient, private authService: AuthService) { }

  postSpecialOffer(specialOffer: any): Observable<any> {
    return this.http.post(this.baseUrl, specialOffer, { headers : this.authService.getHeader() });
  }

  getSpecialOffers(entityId: number): Observable<any> {
    let options = {
      params: new HttpParams().set('entityId', entityId),
      headers: this.authService.getHeader()
    } 
    
    return this.http.get(this.baseUrl, options);
  }
}
