import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ticket } from './ticket.model';

@Injectable({ providedIn: 'root' })
export class TicketRepository {
  private API_URL = 'http://localhost:8082/api/v1/tickets';

  constructor(private http: HttpClient) {}

  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.API_URL);
  }

  getTicket(id: number, ticket: Partial<Ticket>): Observable<Ticket>{
    return this.http.get<Ticket>(`${this.API_URL}/${id}`);
  }

  createTicket(ticket: Partial<Ticket>): Observable<Ticket> {
    return this.http.post<Ticket>(this.API_URL, ticket);
  }

  updateTicket(id: number, ticket: Partial<Ticket>): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.API_URL}/${id}`, ticket);
  }

}