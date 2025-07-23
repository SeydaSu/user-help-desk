import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Ticket } from '../../store/ticket.model';

@Component({
  selector: 'app-ticket-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ticket-list.html',
  styleUrl: './ticket-list.css'
})
export class TicketListComponent {
  tickets$: Observable<Ticket[]>;

  // backendle veri bağlantısı sağlamadan önce deneme
  constructor() {
   this.tickets$ = of([
  {
    id: 1,
    title: 'Test Ticket 1',
    description: 'This is a test ticket.',
    createdAt: new Date().toISOString(), 
    status: 'OPEN',
    priorityId: 1,
    statusId: 1,
    createdBy: 'user@example.com',
    userId: 1,
    tagId: 1
  },
  {
    id: 2,
    title: 'Test Ticket 2',
    description: 'Another test ticket.',
    createdAt: new Date().toISOString(), 
    status: 'IN_PROGRESS',
    priorityId: 2,
    statusId: 2,
    createdBy: 'admin@example.com',
    userId: 2,
    tagId: 2
  }
]);


  }


  goToDetails(id: number): void {
  //this.router.navigate(['/tickets/details', id]);
}

}
