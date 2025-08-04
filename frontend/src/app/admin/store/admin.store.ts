import { Injectable } from '@angular/core';
import { createStore, withProps } from '@ngneat/elf';
import { Status } from '../../models/status.model';
import { Priority } from '../../models/priority.model';
import { Tag } from '../../models/tag.model';

export interface AdminState {
  statuses: Status[];
  priorities: Priority[];
  tags: Tag[];
}

const initialState: AdminState = {
  statuses: [],
  priorities: [],
  tags: []
};

export const adminStore = createStore(
  { name: 'admin' },
  withProps<AdminState>(initialState)
);


@Injectable({ providedIn: 'root' })
export class TicketStoreService {
  store = adminStore;
}
