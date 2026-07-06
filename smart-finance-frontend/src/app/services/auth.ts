import { Injectable } from '@angular/core';

import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Auth {

  private baseUrl =
    `${environment.apiUrl}/api/auth`;

  constructor(
    private http: HttpClient
  ) {}

  // ✅ REGISTER
  register(user: any) {

    return this.http.post(
      `${this.baseUrl}/register`,
      user
    );
  }

  // ✅ LOGIN
  login(credentials: any) {

    return this.http.post(
      `${this.baseUrl}/login`,
      credentials,
      {
        responseType: 'text'
      }
    );
  }

  // ✅ SAVE TOKEN
  saveToken(token: string) {

    localStorage.setItem(
      'token',
      token
    );
  }

  // ✅ GET TOKEN
  getToken() {

    return localStorage.getItem(
      'token'
    );
  }

  // ✅ CHECK LOGIN
  isLoggedIn() {

    return !!this.getToken();
  }

  // ✅ LOGOUT
  logout() {

    localStorage.removeItem(
      'token'
    );
  }

  // ✅ EXTRACT EMAIL FROM JWT
  getUserEmail(): string {

    const token = this.getToken();

    if(!token) {
      return '';
    }

    const payload =
      JSON.parse(atob(token.split('.')[1]));

    return payload.sub;
  }

  // ✅ GENERATE HEADERS
  getHeaders() {

    const token = this.getToken();
  
    if (!token) {
  
      return new HttpHeaders();
    }
  
    return new HttpHeaders({
  
      Authorization: `Bearer ${token}`
    });
  }
}