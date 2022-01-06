import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private baseUrl: string = environment.baseUrl;

    constructor(private http: HttpClient) { }

    login(loginRequest: Object): Observable<any>{
        return this.http.post<any>(this.baseUrl + 'auth', loginRequest);
    }

    setToken(data: string): void {
        localStorage.setItem('jwtToken', data);
    }

    getHeader() : HttpHeaders{
        let token = localStorage.getItem('jwtToken');
        let header = new HttpHeaders().set('Content-Type', 'application/json');
        if(token != null){
            header
            .set('Authorization',  'Bearer' + localStorage.getItem('jwtToken'))
        }
        return header;
    }

    getToken(): any {
        return localStorage.getItem('jwtToken');
    }

}