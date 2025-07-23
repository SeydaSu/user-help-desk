import { Injectable } from '@angular/core';
import {
  addEntities,
  updateEntities,
  deleteEntities,
  setEntities,
  setActiveId,
} from '@ngneat/elf-entities';
import { ticketStore } from './ticket.store';
import { Ticket } from './ticket.model';

@Injectable({ providedIn: 'root' })
export class TicketService {

  setTickets(tickets: Ticket[]) {
    ticketStore.update(setEntities(tickets));
  }

  addTicket(ticket: Ticket) {
    ticketStore.update(addEntities(ticket));
  }

  updateTicket(ticket: Ticket) {
    ticketStore.update(updateEntities(ticket.id, ticket));
  }

  removeTicket(id: number) {
    ticketStore.update(deleteEntities(id));
  }

  setActiveTicket(id: number) {
    ticketStore.update(setActiveId(id));
  }

  setLoading(isLoading: boolean) {
    ticketStore.update((state) => ({
      ...state,
      isLoading,
    }));
  }

  setError(message: string | undefined) {
    ticketStore.update((state) => ({
      ...state,
      error: message,
    }));
  }
}
