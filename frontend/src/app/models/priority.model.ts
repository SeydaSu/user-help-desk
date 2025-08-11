export interface Priority {
  id: number;
  name: string;
  createdBy: string;
}

export interface PriorityResponse {
  id: number;
  name: string;
}

export interface PriorityRequest {
  name: string;
}