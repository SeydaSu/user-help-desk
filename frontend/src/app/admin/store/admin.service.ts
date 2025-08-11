import { Injectable } from '@angular/core';
import { AdminRepository } from './admin.repository';
import { AdminStoreService } from './admin.store';
import { addEntities, setEntities, updateEntities } from '@ngneat/elf-entities';
import { Ticket, TicketAdminUpdateRequest } from '../../models/ticket.model';
import { PriorityRequest } from '../../models/priority.model';
import { StatusRequest } from '../../models/status.model';
import { TagRequest } from '../../models/tag.model';
import { Observable, tap, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(
    private repo: AdminRepository,
    private storeService: AdminStoreService
  ) {}

  loadTickets() {
    this.repo.getAllTicketsForAdmin().pipe(
      catchError(error => {
        console.error('Error loading tickets:', error);
        return throwError(() => error);
      })
    ).subscribe(tickets => {
      this.storeService.store.update(setEntities(tickets));
    });
  }

  getTicketById(id: number): Observable<Ticket> {
    return this.repo.getTicketById(id);
  }

  updateTicket(id: number, update: TicketAdminUpdateRequest): Observable<any> {
    return this.repo.respondToTicket(id, update).pipe(
      tap(() => {
        this.loadTickets();
      }),
      catchError(error => {
        console.error('Error updating ticket:', error);
        return throwError(() => error);
      })
    );
  }

  updatePriority(id: number, update: TicketAdminUpdateRequest): Observable<any> {
    return this.repo.updatePriority(id, update).pipe(
      tap(() => {
        this.loadTickets();
      }),
      catchError(error => {
        console.error('Error updating priority:', error);
        return throwError(() => error);
      })
    );
  }

  updateStatus(id: number, update: TicketAdminUpdateRequest): Observable<any> {
    return this.repo.updateStatus(id, update).pipe(
      tap(() => {
        this.loadTickets();
      }),
      catchError(error => {
        console.error('Error updating status:', error);
        return throwError(() => error);
      })
    );
  }

  updateTag(id: number, update: TicketAdminUpdateRequest): Observable<any> {
    return this.repo.updateTag(id, update).pipe(
      tap(() => {
        this.loadTickets();
      }),
      catchError(error => {
        console.error('Error updating tag:', error);
        return throwError(() => error);
      })
    );
  }

  loadPriorities() {
    this.repo.getAllPriorities().pipe(
      catchError(error => {
        console.error('Error loading priorities:', error);
        return throwError(() => error);
      })
    ).subscribe(priorities => {
      this.storeService.store.update(state => ({
        ...state,
        priorities
      }));
    });
  }

  createPriority(request: PriorityRequest): Observable<any> {
    return this.repo.createPriority(request).pipe(
      tap(() => {
        this.loadPriorities();
      }),
      catchError(error => {
        console.error('Error creating priority:', error);
        return throwError(() => error);
      })
    );
  }

  loadStatuses() {
    this.repo.getAllStatuses().pipe(
      catchError(error => {
        console.error('Error loading statuses:', error);
        return throwError(() => error);
      })
    ).subscribe(statuses => {
      this.storeService.store.update(state => ({
        ...state,
        statuses
      }));
    });
  }

  createStatus(request: StatusRequest): Observable<any> {
    return this.repo.createStatus(request).pipe(
      tap(() => {
        this.loadStatuses();
      }),
      catchError(error => {
        console.error('Error creating status:', error);
        return throwError(() => error);
      })
    );
  }

  loadTags() {
    this.repo.getAllTags().pipe(
      catchError(error => {
        console.error('Error loading tags:', error);
        return throwError(() => error);
      })
    ).subscribe(tags => {
      this.storeService.store.update(state => ({
        ...state,
        tags
      }));
    });
  }

  createTag(request: TagRequest): Observable<any> {
    return this.repo.createTag(request).pipe(
      tap(() => {
        this.loadTags();
      }),
      catchError(error => {
        console.error('Error creating tag:', error);
        return throwError(() => error);
      })
    );
  }
}