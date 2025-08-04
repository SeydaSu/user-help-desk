import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ticket, TicketAdminUpdateRequest, TicketResponse } from '../../models/ticket.model';
import { Priority, PriorityRequest, PriorityResponse } from '../../models/priority.model';
import { Status, StatusRequest, StatusResponse } from '../../models/status.model';
import { Tag, TagRequest, TagResponse } from '../../models/tag.model';


@Injectable({ providedIn: 'root' })
export class AdminRepository {
    
  private adminServiceUrl = 'http://localhost:8085/api/v1/admin/ticket';  

  constructor(private http: HttpClient) {}

    getAllTicketsForAdmin(): Observable<Ticket[]>{
        return this.http.get<Ticket[]>(this.adminServiceUrl);
    }

    getTicketById(id: number): Observable<Ticket>{
        return this.http.get<Ticket>(`${this.adminServiceUrl}/${id}`);
    }
  
    respondToTicket(id: number, ticket: TicketAdminUpdateRequest): Observable<TicketResponse>{
        return this.http.put<TicketResponse>(`${this.adminServiceUrl}/${id}`, ticket)
    }

    updatePriority(id: number, ticket: TicketAdminUpdateRequest): Observable<TicketResponse>{
        return this.http.put<TicketResponse>(`${this.adminServiceUrl}/${id}/priority`, ticket)
    }

    updateStatus(id: number, ticket: TicketAdminUpdateRequest): Observable<TicketResponse>{
        return this.http.put<TicketResponse>(`${this.adminServiceUrl}/${id}/status`, ticket)
    }
    
    updateTag(id: number, ticket: TicketAdminUpdateRequest): Observable<TicketResponse>{
        return this.http.put<TicketResponse>(`${this.adminServiceUrl}/${id}/tag`, ticket)
    }

    getAllPriorities(): Observable<Priority[]> {
        return this.http.get<Priority[]>(`${this.adminServiceUrl}/priorities`);
    }
    
    getAllStatuses(): Observable<Status[]> {
        return this.http.get<Status[]>(`${this.adminServiceUrl}/statuses`);
    }
    
    getAllTags(): Observable<Tag[]> {
        return this.http.get<Tag[]>(`${this.adminServiceUrl}/tags`);
    }

    createPriority(request: PriorityRequest): Observable<PriorityResponse> {
        return this.http.post<PriorityResponse>(`${this.adminServiceUrl}/priorities`, request);
    } 

    creatStatus(request: StatusRequest): Observable<StatusResponse> {
        return this.http.post<StatusResponse>(`${this.adminServiceUrl}/status`, request);
    } 

    createTag(request: TagRequest): Observable<TagResponse> {
        return this.http.post<TagResponse>(`${this.adminServiceUrl}/priorities`, request);
    } 

    
}
