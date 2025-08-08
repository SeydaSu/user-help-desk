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

  tagsMap = new Map<number, string>();
  prioritiesMap = new Map<number, string>();
  statusesMap = new Map<number, string>();

  ngOnInit(): void { 
    this.ticketFacade.getAllTags().subscribe(tags =>{
      tags.forEach(tag => this.tagsMap.set(tag.id, tag.name));
    })
    this.ticketFacade.getAllPriorities().subscribe(priorities =>{
      priorities.forEach(priority => this.prioritiesMap.set(priority.id, priority.name));
    })
    this.ticketFacade.getAllStatuses().subscribe(statuses =>{
      statuses.forEach(status => this.statusesMap.set(status.id, status.name));
    })

    this.route.paramMap
      .pipe(
        switchMap(params => {
          const id = Number(params.get('id'));
          if (id) {
            this.ticketFacade.setActiveTicket(id);
            this.ticketFacade.loadMyTickets();
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
    this.router.navigate(['/ticket/update', ticket.id]);
  }

  

}