import { Routes } from '@angular/router';
import { HomeComponent } from './home/home';
import { LoginComponent } from './auth/login/login';
import { RegisterComponent } from './auth/register/register';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent }, // Yönlendirme yapılacak sayfa

  { path: '**', redirectTo: '' }
];

// auth.service.ts
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { DashboardComponent } from './dashboard/dashboard';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/auth';

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

  register(data: { email: string; username: string; password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, data);
  }

  logout() {
    localStorage.clear();
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getRole(): string {
    return localStorage.getItem('role') || '';
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}