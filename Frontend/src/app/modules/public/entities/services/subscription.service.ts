import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';

@Injectable({
  providedIn: 'any'
})
export class SubscriptionService {

  private baseUrl: string = environment.baseUrl;
    
  constructor(private http: HttpClient, private authService: AuthService) {}

  public toggleSubscribe(subscription: { entityId: number }): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'subscriptions', subscription, { headers: this.authService.getHeader() });
  }

  public checkSubscriptionStatus(entityId: number): Observable<boolean> {
    return this.http.get<boolean>(this.baseUrl + 'subscriptions/' + entityId, { headers: this.authService.getHeader() });
  }
}
