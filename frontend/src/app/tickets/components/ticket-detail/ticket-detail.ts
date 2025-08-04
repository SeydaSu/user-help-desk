import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';
import { TicketFacade } from '../../store/ticket.facade';
import { Ticket } from '../../../models/ticket.model';

@Component({
  selector: 'app-ticket-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ticket-detail.html',
  styleUrl: './ticket-detail.css'
})
export class TicketDetailComponent implements OnInit, OnDestroy {
  ticket$: Observable<Ticket | undefined>;
  isLoading$: Observable<boolean>;
  error$: Observable<string | undefined>;
  
  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketFacade: TicketFacade
  ) {
    this.isLoading$ = this.ticketFacade.isLoading$;
    this.error$ = this.ticketFacade.error$;
    this.ticket$ = this.ticketFacade.activeTicket$;
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap(params => {
          const id = Number(params.get('id'));
          if (id) {
            this.ticketFacade.setActiveTicket(id);
            this.ticketFacade.loadTickets();
          }
          return this.ticket$;
        }),
        takeUntil(this.destroy$)
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  goBack(): void {
    this.router.navigate(['/tickets']);
  }

  editTicket(ticket: Ticket): void {
    this.router.navigate(['/ticket', ticket.id, 'edit']);
  }

  getStatusClass(status: number): string {
    switch (status) {
      case 1:
        return 'status-open';
      case 2:
        return 'status-in-progress';
      case 3:
        return 'status-closed';
      case 4:
        return 'status-resolved';
      default:
        return 'status-default';
    }
  }

  getPriorityClass(priorityId: number): string {
    switch (priorityId) {
      case 1:
        return 'priority-low';
      case 2:
        return 'priority-medium';
      case 3:
        return 'priority-high';
      case 4:
        return 'priority-critical';
      default:
        return 'priority-medium';
    }
  }

  getPriorityText(priorityId: number): string {
    switch (priorityId) {
      case 1:
        return 'Düşük';
      case 2:
        return 'Orta';
      case 3:
        return 'Yüksek';
      case 4:
        return 'Kritik';
      default:
        return 'Orta';
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('tr-TR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}