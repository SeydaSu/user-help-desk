export interface Tag {
  id: number;
  name: string;
}

export interface TagRequest {
  name: string;
}


export interface TagResponse {
  id: number;
  name: string;
  createdBy: string;
  createdAt: string;
}