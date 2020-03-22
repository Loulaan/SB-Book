import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {tap} from 'rxjs/operators';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public API_URL = environment.API_URL;
  public nameSubject$: BehaviorSubject<string> = new BehaviorSubject<string>('');

  constructor(private http: HttpClient) { }

  get userName() {
    return this.nameSubject$.asObservable();
  }


  setLogin(res: any) {
    localStorage.setItem('jwt_token', res.token);
    localStorage.setItem('refresh_token', res.refreshToken);
    return res;
  }

  logout() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('refresh_token');
    this.nameSubject$.next('');
  }

  login(data): Observable<any> {
    this.nameSubject$.next(data.username);
    return this.http.post<any>(`${this.API_URL}/users/login`, data).pipe(tap((res: any) => this.setLogin(res)));
  }

  signup(data): Observable<any> {
    return this.http.post<any>(`${this.API_URL}/users/registration/`, data);
  }

}
