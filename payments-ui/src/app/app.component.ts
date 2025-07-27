import {Component, OnInit} from '@angular/core';
import {AuthService} from "./services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'camunda-sample';
  username: string | null = '';


  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    debugger
    this.username = this.authService.getCurrentUser();
    this.router.navigate(['/home']);
  }

  logout() {
    this.username = '';
    this.authService.logout();
  }
}
