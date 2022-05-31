import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { EntitySubscription } from '../models/entity-subscription.model';

@Injectable({
  providedIn: 'any'
})
export class SubscriptionService {


  private baseUrl: string = environment.baseUrl + "subscriptions";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getSubscriptionList(): Observable<Array<EntitySubscription>> {
    return this.http.get<Array<EntitySubscription>>(this.baseUrl, { headers: this.authService.getHeader() });
  }

  unsubscribe(id: number): Observable<any> {
    let subscription = { entityId: id }
    return this.http.post<any>(this.baseUrl, subscription, { headers: this.authService.getHeader() });
  }

}
