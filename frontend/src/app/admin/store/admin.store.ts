import { Injectable } from '@angular/core';
import { createStore, withProps } from '@ngneat/elf';
import {
  EntitiesState,
  withEntities,
  withActiveId,
} from '@ngneat/elf-entities';
import { Ticket } from '../../models/ticket.model';
import { Priority } from '../../models/priority.model';
import { Status } from '../../models/status.model';
import { Tag } from '../../models/tag.model';

export interface AdminState {
  priorities: Priority[];
  statuses: Status[];
  tags: Tag[];
}

export const adminStore = createStore(
  { name: 'admin' },
  withEntities<Ticket>(),
  withActiveId(),
  withProps<AdminState>({
    priorities: [],
    statuses: [],
    tags: [],
  })
);

@Injectable({ providedIn: 'root' })
export class AdminStoreService {
  store = adminStore;
}
