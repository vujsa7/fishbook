import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RuleService {
  private baseUrl: string = environment.baseUrl + 'rules';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getRules(type: string): Observable<any> {
    let params = new HttpParams().set('type', type);
    return this.http.get(this.baseUrl, { headers: this.authService.getHeader(), params: params});
  }
}
