import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ticket, TicketRequest, TicketResponse } from '../../models/ticket.model';
import { PriorityResponse } from '../../models/priority.model';
import { StatusResponse } from '../../models/status.model';
import { Tag, TagResponse } from '../../models/tag.model';


@Injectable({ providedIn: 'root' })
export class TicketRepository {
  private apiUrl = 'http://localhost:8082/api/v1/ticket';
  private adminServiceUrl = 'http://localhost:8085/api/v1/admin';  // priority ve status için
  private tagServiceUrl = 'http://localhost:8084/api/v1/tags';  // tag için

  constructor(private http: HttpClient) {}

  getMyTickets(): Observable<Ticket[]> {
  const token = localStorage.getItem('token') || '';
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });

  return this.http.get<Ticket[]>(`${this.apiUrl}/my`, { headers });
  }

  getAllTicketsForAdmin(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiUrl}/admin/tickets`);
  }

  getTicket(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiUrl}/${id}`);
  }

  createTicket(ticket: TicketRequest): Observable<TicketResponse> {
  const token = localStorage.getItem('token') || '';
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });

  return this.http.post<TicketResponse>(this.apiUrl, ticket, { headers });
}

  updateTicket(id: number, ticket: TicketRequest): Observable<TicketResponse> {
    return this.http.post<TicketResponse>(`${this.apiUrl}/update/${id}`, ticket);
  }

  getAllPriorities(): Observable<PriorityResponse[]> {
    return this.http.get<PriorityResponse[]>(`${this.adminServiceUrl}/ticket/priorities`);
  }

  getAllStatuses(): Observable<StatusResponse[]> {
    return this.http.get<StatusResponse[]>(`${this.adminServiceUrl}/ticket/statuses`);
  }

  getAllTags(): Observable<TagResponse[]> {
    return this.http.get<TagResponse[]>(`${this.adminServiceUrl}/ticket/tags`);
  }
}
