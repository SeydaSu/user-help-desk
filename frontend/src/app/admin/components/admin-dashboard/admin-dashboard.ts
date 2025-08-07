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
import { LeftSidebarComponent } from '../../../left-sidebar/left-sidebar.component';


import { Auth } from '../../../core/services/auth';
import { AuthService } from '../../../auth/auth.service';


@Component({
  selector: 'app-admin-dashboard',
  imports: [ CommonModule, LeftSidebarComponent,RouterModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css'
})
export class AdminDashboard {
   user: any;
  
    items = [
      {
        routerLink: '/admin-dashboard',
        icon: 'fal fa-home',
        label: 'Admin Dashboard'
      }
      , {
        routerLink: '/tickets',
        icon: 'fal fa-ticket-alt',
        label: 'Tickets'
      }, {
        routerLink: '/add-tags',
        icon: 'fal fa-ticket-alt',
        label: 'Add Tags'
      }, {
        routerLink: '/add-priorities',
        icon: 'fal fa-users',
        label: 'Add Priorities'
      }, {
        routerLink: '/add-statuses',
        icon: 'fal fa-cog',
        label: 'Add Statuses'
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
