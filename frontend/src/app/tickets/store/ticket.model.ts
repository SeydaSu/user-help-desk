export interface Ticket {
  id: number;
  title: string;
  description: string;
  response?: string;
  priorityId: number;
  statusId: number;
  createdAt: string;
  updatedAt?: string;
  createdBy: string;
  userId: number;
  tagId: number;
}

export enum Priority {
  LOW = 1,
  MEDIUM = 2,
  HIGH = 3,
}

export enum Status {
  OPEN = 1,
  IN_PROGRESS = 2,
  CLOSED = 3,
}

export interface Tag {
  id: number;
  name: string;
}
