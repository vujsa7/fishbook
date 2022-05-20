import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { EntitySubscription } from '../models/entity-subscription.model';
import entitySubscriptions from 'src/assets/mocks/entity-subscription-list.json';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'any'
})
export class SubscriptionService {
  

  private baseUrl: string = environment.baseUrl + "subscriptions";

  constructor(private http: HttpClient, private authService: AuthService) { }

  getSubscriptionList(): Array<EntitySubscription> {
    return entitySubscriptions as Array<EntitySubscription>;
  }

  unsubscribe(id: number) : Observable<any>{
    // TODO: unsubscribe from entity with id
    return new Observable(subscriber => {
      subscriber.next("unsubscribed");
    })
  }
}
