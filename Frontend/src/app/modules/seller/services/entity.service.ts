import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';
import { AdventureDetails } from '../../public/entities/models/adventure.details.model';
import { BoatDetails } from '../../public/entities/models/boat-details.model';
import { HouseDetails } from '../../public/entities/models/house-details.model';
import { EntityStatistic } from '../models/entity-statistic.model';

@Injectable({
  providedIn: 'root'
})
export class EntityService {


  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient, private authService: AuthService) { }

  public fetchAdventureDetails(id: number): Observable<AdventureDetails> {
    return this.http.get<AdventureDetails>(this.baseUrl + "fishingLessons/" + id);
  }

  public fetchBoatDetails(id: number): Observable<BoatDetails> {
    return this.http.get<BoatDetails>(this.baseUrl + "boats/" + id);
  }

  public fetchHouseDetails(id: number): Observable<HouseDetails> {
    return this.http.get<HouseDetails>(this.baseUrl + "houses/" + id);
  }

  public getEntityStatics(): Observable<EntityStatistic[]> {
    if (this.authService.getTokenRole() == 'ROLE_BOAT_OWNER') {
      return this.http.get<EntityStatistic[]>(this.baseUrl + "boats/statistics?ownerUsername=" + this.authService.getTokenUsername());
    } else if (this.authService.getTokenRole() == 'ROLE_HOUSE_OWNER') {
      return this.http.get<EntityStatistic[]>(this.baseUrl + "houses/statistics?ownerUsername=" + this.authService.getTokenUsername());
    } else {
      return this.http.get<EntityStatistic[]>(this.baseUrl + "fishingLessons/statistics?ownerUsername=" + this.authService.getTokenUsername());
    }

  }
  
}
