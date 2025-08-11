import { Injectable } from '@angular/core';
import { selectAllEntities, selectActiveEntity } from '@ngneat/elf-entities';
import { adminStore } from './admin.store';
import { map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminQuery {

  allTickets$ = adminStore.pipe(selectAllEntities());

  activeTicket$ = adminStore.pipe(selectActiveEntity());

  priorities$ = adminStore.pipe(map(state => state.priorities));
  
  statuses$ = adminStore.pipe(map(state => state.statuses));

  tags$ = adminStore.pipe(map(state => state.tags));
}
