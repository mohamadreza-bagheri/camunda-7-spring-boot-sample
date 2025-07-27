import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private apiUrl = 'http://localhost:8082/api/history';

  constructor(
    private http: HttpClient
  ) { }

  getUserTaskHistory(userId: string) {
    return this.http.get<any[]>(`${this.apiUrl}/user-tasks/${userId}`);
  }

  getProcessHistory(processInstanceId: string) {
    return this.http.get<any[]>(`${this.apiUrl}/process-tasks/${processInstanceId}`);
  }

}
