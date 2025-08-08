import { Injectable } from '@angular/core';
import { catchError, finalize, tap } from 'rxjs/operators';
import { TicketQuery } from '../store/ticket.query';
import { TicketService } from '../store/ticket.service';
import { TicketRepository } from '../store/ticket.repository';
import { Observable, throwError } from 'rxjs';
import { Ticket, TicketRequest } from '../../models/ticket.model';

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

  getAllTags() {
  return this.ticketRepository.getAllTags();
  }

  getAllPriorities() {
  return this.ticketRepository.getAllPriorities();
  }

  getAllStatuses() {
  return this.ticketRepository.getAllStatuses();
  }


  setTickets(tickets: Ticket[]) {
    this.ticketService.setTickets(tickets);
  }


  loadMyTickets() {
  this.ticketService.setLoading(true);
  this.ticketRepository
    .getMyTickets()
    .pipe(finalize(() => this.ticketService.setLoading(false)))
    .subscribe({
      next: (tickets) => this.ticketService.setTickets(tickets),
      error: () => this.ticketService.setError('Kullanıcı ticketları yüklenemedi'),
    });
}

  createTicket(ticket: TicketRequest): Observable<Ticket> {
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


  updateTicket(id: number, ticket: TicketRequest) {
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
