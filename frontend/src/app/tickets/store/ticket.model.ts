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

export interface PriorityResponse {
  id: number;
  name: string;
}

export interface StatusResponse {
  id: number;
  name: string;
}

export interface Tag {
  id: number;
  name: string;
}
