import { CommonModule } from '@angular/common';
import {
  Component,
  computed,
  HostListener,
  input,
  OnInit,
  signal,
} from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { LeftSidebarComponent } from '../left-sidebar/left-sidebar.component';

import { HomeComponent } from '../home/home';
import { Auth } from '../core/services/auth';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [ CommonModule, LeftSidebarComponent,RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  user: any;

  items = [
    {
      routerLink: '/dashboard',
      icon: 'fal fa-home',
      label: 'Dashboard'
    }
    , {
      routerLink: '/ticket',
      icon: 'fal fa-ticket-alt',
      label: 'Create Ticket'
    }, {
      routerLink: '/tickets',
      icon: 'fal fa-ticket-alt',
      label: 'Tickets'
    }, {
      routerLink: '/user-profile',
      icon: 'fal fa-users',
      label: 'Profile'
    }, {
      routerLink: '/settings',
      icon: 'fal fa-cog',
      label: 'Settings'
    }

  ];
  
  

  constructor(private authService: AuthService,private router: Router) {

    console.log(this.user?.name); 

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
