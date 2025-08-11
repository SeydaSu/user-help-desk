import { Injectable } from '@angular/core';
import { selectAllEntities, selectActiveEntity } from '@ngneat/elf-entities';
import { ticketStore } from './ticket.store';
import { map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TicketQuery {

  tickets$ = ticketStore.pipe(selectAllEntities());

  activeTicket$ = ticketStore.pipe(selectActiveEntity());

  isLoading$ = ticketStore.pipe(map(state => state.isLoading));

  error$ = ticketStore.pipe(map(state => state.error));
}
