import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { EntityImage } from 'src/app/modules/seller/models/entity-image.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private baseUrl: string = environment.baseUrl;
  private header: HttpHeaders;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.header = authService.getFormHeader();
   }

  postImage(image: any): Observable<EntityImage> {
    return this.http.post<any>(this.baseUrl + "files/image", image, { headers: this.header });
  }
}
