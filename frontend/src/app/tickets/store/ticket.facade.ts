import { Injectable } from '@angular/core';
import { catchError, finalize, tap } from 'rxjs/operators';
import { TicketQuery } from '../store/ticket.query';
import { TicketService } from '../store/ticket.service';
import { TicketRepository } from '../store/ticket.repository';
import { Ticket } from '../store/ticket.model';
import { Observable, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TicketFacade {
  constructor(
    private ticketQuery: TicketQuery,
    private ticketService: TicketService,
    private ticketRepository: TicketRepository
  ) {}

  get tickets$() {
    return this.ticketQuery.tickets$;
  }

  get activeTicket$() {
    return this.ticketQuery.activeTicket$;
  }

  get isLoading$() {
    return this.ticketQuery.isLoading$;
  }

  get error$() {
    return this.ticketQuery.error$;
  }

  setTickets(tickets: Ticket[]) {
    this.ticketService.setTickets(tickets);
  }


  loadTickets() {
    this.ticketService.setLoading(true);
    this.ticketRepository
      .getTickets()
      .pipe(finalize(() => this.ticketService.setLoading(false)))
      .subscribe({
        next: (tickets) => this.ticketService.setTickets(tickets),
        error: (err) => this.ticketService.setError('Ticketlar yüklenemedi'),
      });
  }

  createTicket(ticket: Partial<Ticket>): Observable<Ticket> {
  this.ticketService.setLoading(true);
  return this.ticketRepository.createTicket(ticket).pipe(
    finalize(() => this.ticketService.setLoading(false)),
    tap((newTicket) => this.ticketService.addTicket(newTicket)),
    catchError((error) => {
      this.ticketService.setError('Ticket oluşturulamadı');
      return throwError(() => error);
    })
  );
}


  updateTicket(id: number, ticket: Partial<Ticket>) {
    this.ticketService.setLoading(true);
    this.ticketRepository
      .updateTicket(id, ticket)
      .pipe(finalize(() => this.ticketService.setLoading(false)))
      .subscribe({
        next: (updatedTicket) => this.ticketService.updateTicket(updatedTicket),
        error: (err) => this.ticketService.setError('Ticket güncellenemedi'),
      });
  }

  setActiveTicket(id: number) {
    this.ticketService.setActiveTicket(id);
  }
}
