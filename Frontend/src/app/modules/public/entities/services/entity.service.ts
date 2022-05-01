import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityBasicInfo } from 'src/app/modules/public/entities/models/entity-basic-info.model';
import { environment } from 'src/environments/environment';
import boats from 'src/assets/mocks/boats.json';
import houses from 'src/assets/mocks/houses.json';
import adventures from 'src/assets/mocks/adventures.json';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  private baseUrl: string = environment.baseUrl;
    
  constructor(private http: HttpClient) {}

  public fetchEntitiesBasicInfo(entityType: string): Array<EntityBasicInfo>{
    if(entityType == "boats")
      return boats as Array<EntityBasicInfo>;
    else if(entityType == "houses")
      return houses as Array<EntityBasicInfo>;
    else if(entityType == "adventures")
      return adventures as Array<EntityBasicInfo>;
    else return new Array<EntityBasicInfo>();
  }

}
