import { Component, OnInit } from '@angular/core';
import { PaymentService } from 'src/app/services/payment.service';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-payment-editor',
  templateUrl: './payment-editor.component.html',
  styleUrls: ['./payment-editor.component.scss']
})
export class PaymentEditorComponent implements OnInit {
  loading = false;
  userName: string = '';

  paymentGroupId!: any;
  firstName!: any;
  lastName!: any;
  paymentAmount!: any;

  constructor(
    private paymentService: PaymentService,
    private authService: AuthService,
    private router: Router
  ) { }

   ngOnInit() {
     this.userName = this.authService.getCurrentUser() ?? '';
     if(this.userName == '') {
       alert('کاربر نامعتبر است');
       this.router.navigate(['/login']);
       return;
     }
  }

  onSubmit() {
    this.loading = true;
    const data = {
      paymentGroupId: this.paymentGroupId,
      firstName: this.firstName,
      lastName: this.lastName,
      paymentAmount: this.paymentAmount,
      creationUser: this.userName
    }
    this.paymentService.create(data).subscribe({
      next: value => {
        this.loading = false;
        this.firstName = '';
        this.lastName = '';
        this.paymentAmount = '';
        alert('saved successfully!');
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    });
  }
}
