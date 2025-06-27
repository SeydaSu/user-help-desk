import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {
  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/']);
    }
  }

  goToLogin() {
    console.log('Login butonuna tıklandı'); // Debug için
    this.router.navigate(['/login']).then(
      (success) => console.log('Login navigation success:', success),
      (error) => console.error('Login navigation error:', error)
    );
  }

  goToRegister() {
    console.log('Register butonuna tıklandı'); // Debug için
    this.router.navigate(['/register']).then(
      (success) => console.log('Register navigation success:', success),
      (error) => console.error('Register navigation error:', error)
    );
  }
}