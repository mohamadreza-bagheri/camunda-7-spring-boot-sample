import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8082/api/auth';
  private readonly SESSION_STORAGE_KEY = 'currentUser';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  //qwgqwgfqwg

  login(username: string, password: string): void {
    const  model = {
      username: username,
      password: password
    }
    this.http.post(`${this.apiUrl}/login`, model).subscribe({
      next: value => {
        sessionStorage.setItem(this.SESSION_STORAGE_KEY, username);
        this.router.navigate(['/']);
      },
      error: err => {
        alert('خطا در ورود' + err.data);
      }
    })
  }

  register(username: string, password: string, firstName: string, lastName: string, groups: string[]): void {
    const  model = {
      username: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      groups: groups
    }
    this.http.post(`${this.apiUrl}/sync`, model).subscribe({
      next: value => {
        //sessionStorage.setItem(this.SESSION_STORAGE_KEY, username);
        this.router.navigate(['/']);
      },
      error: err => {
        alert('خطا در ایجاد کاربر' + err.data);
      }
    })
  }

  logout(): void {
    sessionStorage.removeItem(this.SESSION_STORAGE_KEY);
    this.router.navigate(['/login']);
  }

  getCurrentUser(): string | null {
    return sessionStorage.getItem(this.SESSION_STORAGE_KEY) ?? '';
  }

  isLoggedIn(): boolean {
    return this.getCurrentUser() !== null;
  }

  getAllGroups() {
    return this.http.get<any[]>(`${this.apiUrl}/all-groups`);
  }

  getUserList() {
    return this.http.get<any[]>(`${this.apiUrl}/users`);
  }
}
