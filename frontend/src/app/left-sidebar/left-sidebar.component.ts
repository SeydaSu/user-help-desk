import { CommonModule } from '@angular/common';
import { Component, input, output } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-left-sidebar',
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './left-sidebar.component.html',
  styleUrl: './left-sidebar.component.css'
})
export class LeftSidebarComponent {

  isLeftSideBarCollapsed = input.required<boolean>();
  changeIsLeftSideBarCollapsed = output<boolean>();
 
  items = [
    {
      RouterLink: '/dashboard',
      icon: 'fal fa-home',
      label: 'Dashboard'
    }
    , {
      RouterLink: '/tickets',
      icon: 'fal fa-ticket-alt',
      label: 'Crete Ticket'
    }, {
      RouterLink: '/tickets',
      icon: 'fal fa-ticket-alt',
      label: 'Tickets'
    }, {
      RouterLink: '/users',
      icon: 'fal fa-users',
      label: 'Profile'
    }, {
      RouterLink: '/settings',
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
