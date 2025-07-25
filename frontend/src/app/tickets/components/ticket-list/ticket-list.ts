import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Ticket } from '../../store/ticket.model';
import { Router } from '@angular/router';
import { TicketFacade } from '../../store/ticket.facade';

@Component({
  selector: 'app-ticket-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css'
})
export class TicketListComponent implements OnInit {
  tickets$: Observable<Ticket[]>;
  isLoading$: Observable<boolean>;
  error$: Observable<string | undefined>;

  private testTickets: Ticket[] = [
    {
      id: 1,
      title: 'Test Ticket 1',
      description: 'This is a test ticket with detailed description. It contains information about the issue that needs to be resolved.',
      createdAt: new Date().toISOString(),
      priorityId: 3,
      statusId: 3,
      createdBy: 'user@example.com',
      userId: 1,
      tagId: 1
    },
    {
      id: 2,
      title: 'Test Ticket 2',
      description: 'Another test ticket with different status and priority. This one is currently being worked on by the development team.',
      createdAt: new Date().toISOString(),
      priorityId: 2,
      statusId: 2,
      createdBy: 'admin@example.com',
      userId: 2,
      tagId: 2
    }
  ];

  constructor(
    private router: Router,
    private ticketFacade: TicketFacade
  ) {
    this.tickets$ = this.ticketFacade.tickets$;
    this.isLoading$ = this.ticketFacade.isLoading$;
    this.error$ = this.ticketFacade.error$;
  }

  ngOnInit(): void {
    // Test verileri için - backend bağlantısı kurulduğunda kaldırılacak
    this.ticketFacade.setTickets(this.testTickets);
  }

  goToDetails(id: number): void {
    this.router.navigate(['/ticket', id]);
  }

  refreshTickets(): void {
    // Test verileri için
    this.ticketFacade.setTickets(this.testTickets);
  }
}