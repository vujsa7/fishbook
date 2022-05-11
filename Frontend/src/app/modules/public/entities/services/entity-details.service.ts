import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HouseDetails } from '../models/house-details.model';
import { BoatDetails } from '../models/boat-details.model';
import houseDetails from 'src/assets/mocks/house-details.json';
import boatDetails from 'src/assets/mocks/boat-details.json';
import adventureDetails from 'src/assets/mocks/adventure-details.json';
import { AdventureDetails } from '../models/adventure.details.model';

@Injectable({
  providedIn: 'any'
})
export class EntityDetailsService {

  private baseUrl: string = environment.baseUrl;
    
  constructor(private http: HttpClient) {}

  public fetchHouseDetails(id: string): HouseDetails{
      // TODO: Fetch from backend using id
      return houseDetails as HouseDetails;
  }

  public fetchBoatDetails(id: string): BoatDetails{
    // TODO: Fetch from backend using id
    return boatDetails as BoatDetails;
  }

  public fetchAdventureDetails(id:string): AdventureDetails{
    // TODO: Fetch from backend using id
    return adventureDetails as AdventureDetails;
  }
}
