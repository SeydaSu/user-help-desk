import { Component, NgModule } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, ReactiveFormsModule ],
  providers: [HttpClient],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  constructor(private http: HttpClient) {} 
  protected title = 'frontend';
}
