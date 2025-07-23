import { Injectable } from '@angular/core';
import { createStore, withProps } from '@ngneat/elf';
import {
  EntitiesState,
  withEntities,
  withActiveId,
} from '@ngneat/elf-entities';
import { Ticket } from './ticket.model';

export interface TicketState extends EntitiesState<Ticket> {
  isLoading: boolean;
  error?: string;
}

export const ticketStore = createStore(
  { name: 'ticket' },
  withEntities<Ticket>(),
  withActiveId(),
  withProps<TicketState>({
    isLoading: false,
    error: undefined,
  })
);

@Injectable({ providedIn: 'root' })
export class TicketStoreService {
  store = ticketStore;
}
