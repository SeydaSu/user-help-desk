import { CommonModule } from '@angular/common';
import {
  Component,
  computed,
  HostListener,
  input,
  OnInit,
  signal,
} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LeftSidebarComponent } from '../left-sidebar/left-sidebar.component';

import { HomeComponent } from '../home/home';
import { Auth } from '../core/services/auth';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [RouterOutlet, CommonModule, LeftSidebarComponent, HomeComponent],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  user: any;

  constructor(private authService: AuthService) {

    console.log(this.user?.name); // "Şeyda Yılmaz"

  }

  logout() {
    this.authService.logout();
    
  }

  isLeftSideBarCollapsed = signal<boolean>(false);
  screenWidth = signal<number>(window.innerWidth);

  @HostListener('window:resize')
  onResize(): void {
    this.screenWidth.set(window.innerWidth);
    if (this.screenWidth() < 768) {
      this.isLeftSideBarCollapsed.set(true);
    }
  }

  ngOnInit(): void {
    this.isLeftSideBarCollapsed.set(this.screenWidth() < 768);
  }

  changeIsLeftSideBarCollapsed(isLeftSideBarCollapsed: boolean): void {
    this.isLeftSideBarCollapsed.set(isLeftSideBarCollapsed);
  }

  sizeClass = computed(() => {
    const isLeftSideBarCollapsed = this.isLeftSideBarCollapsed();
    if (isLeftSideBarCollapsed) {
      return ' ';
    }
    return this.screenWidth() > 768 ? 'body-trimmed' : 'body-md-screen';
  });

  

}
