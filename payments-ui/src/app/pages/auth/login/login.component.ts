import {Component, OnInit} from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.username.trim() && this.password.trim()) {
      this.authService.login(this.username.trim(), this.password.trim());
    }
  }
}
