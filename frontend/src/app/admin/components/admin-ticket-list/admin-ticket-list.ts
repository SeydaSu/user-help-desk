import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Ticket } from '../../../models/ticket.model';
import { AdminFacade } from '../../store/admin.facade';
import { TicketFacade } from '../../../tickets/store/ticket.facade';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-ticket-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-ticket-list.html',
  styleUrl: './admin-ticket-list.css'
})
export class AdminTicketListComponent implements OnInit {

  allTickets$: Observable<Ticket[]>;

  constructor(private router: Router, private adminFacade: AdminFacade, private ticketFacade: TicketFacade  ) {
    this.allTickets$ = this.adminFacade.allTickets$;
  }

  tagsMap = new Map<number, string>();

  tickets: Ticket[] = [];
  currentPage = 1;
  pageSize = 8;

  get paginatedTickets(): Ticket[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.tickets.slice(start, start + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.tickets.length / this.pageSize);
  }
  
  ngOnInit(): void {
    this.adminFacade.getAllTickets();

    this.allTickets$.subscribe((tickets) => {
      this.tickets = tickets;
    });

    this.ticketFacade.getAllTags().subscribe((tags) => {
      tags.forEach((tag) => this.tagsMap.set(tag.id, tag.name));
    });
  }

  goToDetails(id: number): void {
    this.router.navigate(['/ticket', id]);
  }

  refreshTickets(): void {
    this.adminFacade.refreshTickets();
  }

}
