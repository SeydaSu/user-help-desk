import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { Observable, tap } from "rxjs";
import { AuthenticationResponse } from "../core/models/authentication_response";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/v1/auth';

  constructor(private http: HttpClient) {}

  login(data: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, data).pipe(
      tap(res => {
        if (res.token) {
          localStorage.setItem('token', res.token);
        }
        if (res.role) {
          localStorage.setItem('role', res.role);
        }
      })
    );
  }

  login_admin(data: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/admin/login`, data).pipe(
      tap(res => {
        if (res.token) {
          localStorage.setItem('token', res.token);
        }
        if (res.role) {
          localStorage.setItem('role', res.role);
        }
      })
    );
  }

  register(data: { email: string; username: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, data);
  }
 
  isAuthenticated(): boolean {
  if (typeof window !== 'undefined') {
    return !!localStorage.getItem('token');
  }
  return false;
}

getToken(): string | null {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('token');
  }
  return null;
}

getRole(): string {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('role') || '';
  }
  return '';
}

decodeToken(): any {
  const token = localStorage.getItem('token');
  if (!token) return null;

  const payload = token.split('.')[1];
  const decoded = atob(payload);
  return JSON.parse(decoded);
}



  logout(){
    localStorage.clear();
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
  }
}
