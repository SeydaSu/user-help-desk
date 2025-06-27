import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {
  errorMsg: { [key: string]: string } = {};

  isLoading = false;

  // Değişken ismi düzeltildi
  registrationRequest = {
    email: '',
    username: '',
    password: ''
  };

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  register() {
  this.errorMsg = {};
  this.isLoading = true;

  // Email kontrolü
  if (!this.registrationRequest.email) {
    this.errorMsg['email'] = 'Email gereklidir!';
  } else {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.registrationRequest.email)) {
      this.errorMsg['email'] = 'Geçerli bir email adresi giriniz!';
    }
  }

  // Username kontrolü (örneğin en az 3 karakter)
  if (!this.registrationRequest.username) {
    this.errorMsg['username'] = 'Kullanıcı adı gereklidir!';
  } else if (this.registrationRequest.username.length < 3) {
    this.errorMsg['username'] = 'Kullanıcı adı en az 3 karakter olmalıdır!';
  }

  // Password kontrolü (örneğin en az 6 karakter)
  if (!this.registrationRequest.password) {
    this.errorMsg['password'] = 'Şifre gereklidir!';
  } else if (this.registrationRequest.password.length < 6) {
    this.errorMsg['password'] = 'Şifre en az 6 karakter olmalıdır!';
  }

  if (Object.keys(this.errorMsg).length === 0) {
    this.authService.register(this.registrationRequest).subscribe({
      next: (response) => {
        console.log('Kayıt başarılı:', response);
        this.isLoading = false;
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMsg = {};
        if (error.status === 400 && error.error && typeof error.error === 'object') {
          this.errorMsg = error.error;
        } else if (error.error && error.error.message) {
          this.errorMsg['general'] = error.error.message;
        } else if (error.message) {
          this.errorMsg['general'] = error.message;
        } else {
          this.errorMsg['general'] = 'Kayıt sırasında bir hata oluştu!';
        }
      }
    });
  } else {
    this.isLoading = false;
  }
}




}
