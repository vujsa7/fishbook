import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HouseDetails } from '../models/house-details.model';
import { BoatDetails } from '../models/boat-details.model';
import houseDetails from 'src/assets/mocks/house-details.json';
import boatDetails from 'src/assets/mocks/boat-details.json';
import adventureDetails from 'src/assets/mocks/adventure-details.json';
import { AdventureDetails } from '../models/adventure.details.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'any'
})
export class EntityDetailsService {

  private baseUrl: string = environment.baseUrl;
    
  constructor(private http: HttpClient) {}

  public fetchHouseDetails(id: string): Observable<HouseDetails>{
      return this.http.get<HouseDetails>(this.baseUrl + "houses/" + id);
  }

  public fetchBoatDetails(id: string): Observable<BoatDetails>{
    return this.http.get<BoatDetails>(this.baseUrl + "boats/" + id);
  }

  public fetchAdventureDetails(id:string): Observable<AdventureDetails>{
    return this.http.get<AdventureDetails>(this.baseUrl + "fishingLessons/" + id);
  }
}
