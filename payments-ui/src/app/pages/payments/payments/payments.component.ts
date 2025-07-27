import { Component, OnInit } from '@angular/core';
import { PaymentService} from 'src/app/services/payment.service';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
@Component({
  selector: 'app-payment-list',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentsComponent implements OnInit {
  displayedColumns: string[] = ['contractNo', 'firstName', 'lastName', 'paymentAmount', 'id', 'paymentStatus', 'paymentDate', 'creationDate', "actions"];

  dataSource!: MatTableDataSource<any>;

  loading = true;
  userName: string = '';

  customerName: string = '';
  creditAmount: number = 0;
  id: number = 0;

  constructor(
    private paymentService: PaymentService,
    private authService: AuthService,
    private router: Router
  ) {
    this.dataSource = new MatTableDataSource();
  }

   ngOnInit() {
     this.userName = this.authService.getCurrentUser() ?? '';
     if(this.userName == '') {
       alert('کاربر نامعتبر است');
       this.router.navigate(['/login']);
       return;
     }
    this.loadData();
  }

  startProcess(payment: any) {
    /*
    this.loading = true;
    this.camundaService.startProcess(credit).subscribe({
      next: value => {
        this.loading = false;
        this.loadData();
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    })
     */
  }

  public loadData() {
    this.loading = true;
    this.paymentService.getContracts(this.userName).subscribe({
      next: value => {
        this.loading = false;
        this.dataSource.data = value;
      },
      error: err => {
        this.loading = false;
        alert(JSON.stringify(err));
      }
    })
  }

}
