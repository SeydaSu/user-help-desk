import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PriorityResponse, StatusResponse, Tag, Ticket } from './ticket.model';

@Injectable({ providedIn: 'root' })
export class TicketRepository {
  private apiUrl = 'http://localhost:8082/api/v1/ticket';
  private adminServiceUrl = 'http://localhost:8085/api/v1';  // priority ve status için
  private tagServiceUrl = 'http://localhost:8084/api/v1/tags';  // tag için

  constructor(private http: HttpClient) {}

  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.apiUrl);
  }

  getTicket(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiUrl}/${id}`);
  }

  createTicket(ticket: Partial<Ticket>): Observable<Ticket> {
    return this.http.post<Ticket>(this.apiUrl, ticket);
  }

  updateTicket(id: number, ticket: Partial<Ticket>): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiUrl}/${id}`, ticket);
  }

  getAllPriorities(): Observable<PriorityResponse[]> {
    return this.http.get<PriorityResponse[]>(`${this.adminServiceUrl}/priorities`);
  }

  getAllStatuses(): Observable<StatusResponse[]> {
    return this.http.get<StatusResponse[]>(`${this.adminServiceUrl}/statuses`);
  }

  getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.tagServiceUrl);
  }
}
