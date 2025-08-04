export interface Status {
  id: number;
  name: string;
  createdBy: string;
}


export interface StatusResponse {
  id: number;
  name: string;
}


export interface StatusRequest {
  name: string;
}