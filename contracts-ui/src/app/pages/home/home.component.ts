import {Component, OnInit} from '@angular/core';
import {ContractService} from "../../services/contract.service";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  currentTasks: any[] = [];
  loading: boolean = false;
  username!: string;

  constructor(
      private camundaService: ContractService,
      private authService: AuthService,
      private router: Router
  ) {
  }

  ngOnInit(): void {
     this.loadMyTasks();
  }

  loadMyTasks() {
    this.loading = true;
    this.username = this.authService.getCurrentUser() ?? '';
    if (!this.username) {
      this.router.navigate(['/login']);
      return;
    }


    this.camundaService.getMyTasks(this.username).subscribe({
      next: value => {
        this.loading = false;
        this.currentTasks = value ?? [];
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    });

    this.loading = false;
  }
}
