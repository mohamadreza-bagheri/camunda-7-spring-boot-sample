import { Component, OnInit } from '@angular/core';
import { ContractService } from 'src/app/services/contract.service';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-contract-editor-list',
  templateUrl: './contract-editor.component.html',
  styleUrls: ['./contract-editor.component.scss']
})
export class ContractEditorComponent implements OnInit {
  loading = false;
  userName: string = '';

  contractGroupId!: any;
  firstName!: any;
  lastName!: any;
  contractAmount!: any;

  constructor(
    private camundaService: ContractService,
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
      contractGroupId: this.contractGroupId,
      firstName: this.firstName,
      lastName: this.lastName,
      contractAmount: this.contractAmount,
      creationUser: this.userName
    }
    this.camundaService.create(data).subscribe({
      next: value => {
        this.loading = false;
        this.firstName = '';
        this.lastName = '';
        this.contractAmount = '';
        alert('saved successfully!');
      },
      error: err => {
        alert(JSON.stringify(err));
      }
    });
  }
}
