import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { Router, RouterModule, Routes } from '@angular/router';




@Component({
  selector: 'app-left-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './left-sidebar.component.html',
  styleUrl: './left-sidebar.component.css'
})
export class LeftSidebarComponent {

  isLeftSideBarCollapsed = input.required<boolean>();
  changeIsLeftSideBarCollapsed = output<boolean>();
   constructor(private router: Router) {}


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
      RouterLink: '/users',
      icon: 'fal fa-users',
      label: 'Profile'
    }, {
      routerLink: '/settings',
      icon: 'fal fa-cog',
      label: 'Settings'
    }

  ];

  toggleCollapse(): void{
    this.changeIsLeftSideBarCollapsed.emit(!this.isLeftSideBarCollapsed());
  }

  closeSidenav(): void {
    this.changeIsLeftSideBarCollapsed.emit(true);
  }
}