import { Component, OnInit } from '@angular/core';
import { ContractService } from 'src/app/services/contract.service';
import {HistoryService} from "../../../services/history.service";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-user-task-his',
  templateUrl: './user-task-history.component.html',
  styleUrls: ['./user-task-history.component.scss']
})
export class UserTaskHistoryComponent implements OnInit {
  users: any[] = [];
  historyList: any[] = [];
  userId!: string;
  loading = true;

  constructor(
    private authService: AuthService,
    private historyService: HistoryService
  ) {}

   ngOnInit() {
    this.loadUsers();
   }

  loadHistory() {
    if (!this.userId) {
      alert('user not selected!')
      return;
    }
    this.loading = true;
    this.historyService.getUserTaskHistory(this.userId).subscribe({
      next: value => {
        this.loading = false;
        this.historyList = value
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    })
  }

  loadUsers() {
    this.authService.getUserList().subscribe({
      next: value => {
        this.loading = false;
        this.users = value
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    })
  }

  getStyle(status: any) {
    switch (status) {
      case 'PENDING':
        return 'background-color: lightyellow;';
      case 'STAGE1_APPROVED':
        return 'background-color: dodgerblue;';
      case 'STAGE2_APPROVED':
        return 'background-color: darkslateblue;';
      case 'STAGE3_APPROVED':
        return 'background-color: lightseagreen;';
      case 'REJECTED':
        return 'background-color: palevioletred;';
      default:
        return 'background-color: white;';
    }
  }
}
