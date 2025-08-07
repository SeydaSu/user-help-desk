import { PriorityResponse } from "./priority.model";
import { StatusResponse } from "./status.model";
import { Tag } from "./tag.model";

export interface Ticket {
  id: number;
  title: string;
  description: string;
  response?: string;

  // ID ile referans (seçim için)
  priorityId: number;
  statusId: number;
  tagId: number;

  // ID ile gelen veriye karşılık gösterim için (opsiyonel ama önerilir)
  priority?: PriorityResponse;
  status?: StatusResponse;
  tag?: Tag;

  createdAt: string;
  updatedAt?: string;
  createdBy: string;
  userId: number;
}

export interface TicketResponse {
  id: number;
  title: string;
  description: string;
  response: string;

  // ID ile referans (seçim için)
  priorityId: number;
  statusId: number;
  tagId: number;

  // ID ile gelen veriye karşılık gösterim için (opsiyonel ama önerilir)
  priority?: PriorityResponse;
  status?: StatusResponse;
  tag?: Tag;


  createdAt: string;
  updatedAt?: string;
  createdBy: string;
  userId: number;

}

export interface TicketRequest {
  title: string;
  description: string;

  // ID ile referans (seçim için)
  priorityId: number;
  statusId: number;
  tagId: number;

  // ID ile gelen veriye karşılık gösterim için (opsiyonel ama önerilir)
  priority?: PriorityResponse;
  status?: StatusResponse;
  tag?: Tag;

 
  userId: number;


}


export interface TicketAdminUpdateRequest {
  ticketId: number;
  statusId: number;
  priorityId: number;
  response: string;
  tagId: number;

  // ID ile gelen veriye karşılık gösterim için (opsiyonel ama önerilir)
  priority?: PriorityResponse;
  status?: StatusResponse;
  tag?: Tag;
}