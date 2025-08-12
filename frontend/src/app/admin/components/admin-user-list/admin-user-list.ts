import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

interface User {
  id: number;
  name: string;
  email: string;
}

@Component({
  selector: 'app-admin-user-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-user-list.html',
  styleUrls: ['./admin-user-list.css'],
})
export class AdminUserListComponent implements OnInit {
  users: User[] = [];
  isLoading = true;
  errorMsg = '';

  constructor(private http: HttpClient) {}
  currentPage = 1;
  pageSize = 12;

  get paginatedUsers(): User[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.users.slice(start, start + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.users.length / this.pageSize);
  }

  ngOnInit(): void {
    this.http
      .get<User[]>('http://localhost:8081/api/v1/user/all', {
        responseType: 'json', 
      })
      .subscribe({
        next: (res) => {
          console.log('User :', res);
          if (Array.isArray(res)) {
            this.users = res.length > 0 ? res : [];
            this.errorMsg = res.length === 0 ? 'No users found.' : '';
          } else {
            this.errorMsg = 'Unexpected data format received.';
          }
          this.isLoading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.errorMsg = `Error: ${err.message}`;
          this.isLoading = false;
        },
      });
  }
}
