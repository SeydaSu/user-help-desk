import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrls: ['./profile.css'],
})
export class ProfileComponent implements OnInit {
  user: any;
  isLoading = true;
  errorMsg = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<any>('http://localhost:8081/api/v1/user/profile').subscribe({
      next: (res) => {
        this.user = res;
        this.isLoading = false;
      },
      error: () => {
        this.errorMsg = 'Profil bilgisi alınamadı.';
        this.isLoading = false;
      },
    });
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
