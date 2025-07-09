import { Component, Host, HostListener, NgModule, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LeftSidebarComponent } from './left-sidebar/left-sidebar.component';
import { Dashboard } from "./dashboard/dashboard";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, ReactiveFormsModule, LeftSidebarComponent, Dashboard],
  providers: [HttpClient],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  constructor(private http: HttpClient) {} 
  protected title = 'User Help Desk';
  isLeftSideBarCollapsed = signal<boolean>(false);
  screenWidth = signal<number>(window.innerWidth);

  @HostListener('window:resize')
  onResize(): void {
    this.screenWidth.set(window.innerWidth);
    if(this.screenWidth() < 768) {
      this.isLeftSideBarCollapsed.set(true);
    }
  }

  ngOnInit(): void {
      this.isLeftSideBarCollapsed.set(this.screenWidth() < 768);
  }
  
  changeIsLeftSideBarCollapsed(isLeftSideBarCollapsed: boolean): void {
    this.isLeftSideBarCollapsed.set(isLeftSideBarCollapsed);
  }
}
