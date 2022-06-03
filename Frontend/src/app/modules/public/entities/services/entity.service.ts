import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { EntityBasicInfo } from 'src/app/shared/models/entity-basic-info.model';
import { Observable } from 'rxjs';
import { SellerAvailability } from '../models/seller-availability.model';
import { EntityAvailability } from '../models/entity-availability.model';

@Injectable({
  providedIn: 'any'
})
export class EntityService {
  
  
  private baseUrl: string = environment.baseUrl;
    
  constructor(private http: HttpClient) {}

  public fetchEntitiesBasicInfo(entityType: string): Observable<Array<EntityBasicInfo>>{
    if(entityType == "adventures")
      return this.http.get<Array<EntityBasicInfo>>(this.baseUrl + "fishingLessons");
    else if(entityType == "houses")
      return this.http.get<Array<EntityBasicInfo>>(this.baseUrl + "houses");
    else if(entityType == "boats")
      return this.http.get<Array<EntityBasicInfo>>(this.baseUrl + "boats");
    else return new Observable<Array<EntityBasicInfo>>();
  }

  fetchSellersAvailabilities() : Observable<Array<SellerAvailability>>{
    return this.http.get<Array<SellerAvailability>>(this.baseUrl + "sellerAvailabilities/all");
  }

  fetchEntityAvailabilities() : Observable<Array<EntityAvailability>>{
    return this.http.get<Array<EntityAvailability>>(this.baseUrl + "entityAvailabilities/all");
  }

}
