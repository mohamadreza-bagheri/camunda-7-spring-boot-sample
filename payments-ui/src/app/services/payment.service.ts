import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = 'http://localhost:8084/api/payments';

  constructor(
    private http: HttpClient
  ) {}

  create(data: any) {
    return this.http.post<any>(`${this.apiUrl}/create`, data);
  }

  getContracts(username: string) {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  approve(approveModel: any, action: string, id: string) {
    return this.http.post(`${this.apiUrl}/approve`, approveModel, {
      params: { action, id }
    });
  }

  getMyTasks(userName: string) {
    return this.http.get<any[]>(`${this.apiUrl}/tasks/${userName}`);
  }

}
