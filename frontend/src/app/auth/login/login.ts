import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  errorMsg: string[] = [];
  isLoading = false;

  // Değişken ismi düzeltildi
  authenticationRequest = {
    email: '',
    password: ''
  };

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  login() {
  this.errorMsg = [];
  this.isLoading = true;

  if (!this.authenticationRequest.email) {
    this.errorMsg.push('Email gereklidir!');
  }
  if (!this.authenticationRequest.password) {
    this.errorMsg.push('Şifre gereklidir!');
  }

  if (this.errorMsg.length === 0) {
    this.authService.login(this.authenticationRequest).subscribe({
      next: (response) => {
        console.log('Login başarılı:', response);
        this.isLoading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMsg = []; // Önce mesajları temizle

        // Backend’den gelen hata mesajını al
        if (error.status === 401) {
          // Yetkisiz, genelde email veya şifre hatası
          this.errorMsg.push('Email veya şifre yanlış.');
        } else if (error.status === 404) {
          this.errorMsg.push('Kullanıcı bulunamadı.');
        } else if (error.error && error.error.message) {
          // Backend özel mesaj gönderiyorsa
          this.errorMsg.push(error.error.message);
        } else if (error.message) {
          this.errorMsg.push(error.message);
        } else {
          this.errorMsg.push('Giriş sırasında bir hata oluştu!');
        }
      }
    });
  } else {
    this.isLoading = false;
  }
}


  goToRegister() {
    this.router.navigate(['/register']);
  }
}
