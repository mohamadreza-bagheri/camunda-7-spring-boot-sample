import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from "../../../services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username = '';
  password = '';
  firstName = '';
  lastName = '';

  groups: any[] = [];
  selectedGroups: any[] = [];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.getAllGroups().subscribe({
      next: value => {
        this.groups = value;
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    });
  }

  onSubmit(): void {
    if (this.username.trim()) {
      this.authService.register(this.username.trim(), this.password.trim(), this.firstName.trim(), this.lastName.trim(), this.selectedGroups);
    }
  }
}
