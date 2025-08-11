import { Injectable } from '@angular/core';
import { AdminQuery } from './admin.query';
import { AdminService } from './admin.service';
import { AdminRepository } from './admin.repository';
import { TicketAdminUpdateRequest } from '../../models/ticket.model';
import { PriorityRequest } from '../../models/priority.model';
import { StatusRequest } from '../../models/status.model';
import { TagRequest } from '../../models/tag.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminFacade {

  constructor(
    private query: AdminQuery,
    private service: AdminService,
    private adminRepository: AdminRepository
  ) {}

  get allTickets$() { return this.query.allTickets$; }
  get activeTicket$() { return this.query.activeTicket$; }
  get priorities$() { return this.query.priorities$; }
  get statuses$() { return this.query.statuses$; }
  get tags$() { return this.query.tags$; }

  initStore() {
    this.service.loadTickets();
    this.service.loadPriorities();
    this.service.loadStatuses();
    this.service.loadTags();
  }

  getAllTickets() {
    return this.service.loadTickets();
  }

  getTicketById(id: number) {
    return this.service.getTicketById(id);
  }

  respondToTicket(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updateTicket(id, payload);
  }
  updatePriority(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updatePriority(id, payload);
  }
  updateStatus(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updateStatus(id, payload);
  }
  updateTag(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updateTag(id, payload);
  }

  setPriority(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updatePriority(id, payload);
  }

  setStatus(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updateStatus(id, payload);
  }

  setTag(id: number, payload: TicketAdminUpdateRequest) {
    return this.service.updateTag(id, payload);
  }

  createPriority(request: PriorityRequest): Observable<any> {
    return this.service.createPriority(request);
  }

  refreshPriorities() {
    return this.service.loadPriorities();
  }

  createStatus(request: StatusRequest): Observable<any> {
    return this.service.createStatus(request);
  }

  refreshStatuses() {
    return this.service.loadStatuses();
  }

  createTag(request: TagRequest): Observable<any> {
    return this.service.createTag(request);
  }

  refreshTags() {
    return this.service.loadTags();
  }

  refreshTickets() {
    return this.service.loadTickets();
  }

  refreshAll() {
    this.initStore();
  }
}