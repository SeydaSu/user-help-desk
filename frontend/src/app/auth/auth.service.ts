import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { tap } from "rxjs";
import { AuthenticationResponse } from "../core/models/authentication_response";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/v1/auth';

  constructor(private http: HttpClient) {}

  login(data: { email: string; password: string }) {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/login`, data).pipe(
      tap(res => {
        localStorage.setItem('token', res.token);
      })
    );
  }

  register(data: { email: string; username: string; password: string }) {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  logout() {
    localStorage.clear();
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
}
