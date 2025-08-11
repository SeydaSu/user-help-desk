import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { TicketFacade } from '../../store/ticket.facade';
import { Ticket } from '../../../models/ticket.model';

@Component({
  selector: 'app-ticket-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css',
})
export class TicketListComponent implements OnInit {
  tickets$: Observable<Ticket[]>;
  isLoading$: Observable<boolean>;
  error$: Observable<string | undefined>;

  constructor(private router: Router, private ticketFacade: TicketFacade) {
    this.tickets$ = this.ticketFacade.tickets$;
    this.isLoading$ = this.ticketFacade.isLoading$;
    this.error$ = this.ticketFacade.error$;
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
    this.ticketFacade.loadMyTickets();

    this.tickets$.subscribe((tickets) => {
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
    this.ticketFacade.loadMyTickets();
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
