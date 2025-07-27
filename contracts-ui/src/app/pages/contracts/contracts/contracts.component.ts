import { Component, OnInit } from '@angular/core';
import { ContractService } from 'src/app/services/contract.service';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {ContractData} from "../contract-data";

@Component({
  selector: 'app-contract-list',
  templateUrl: './contracts.component.html',
  styleUrls: ['./contracts.component.scss']
})
export class ContractsComponent implements OnInit {
  displayedColumns: string[] = ['contractGroupId', 'firstName', 'lastName', 'contractAmount', 'status', 'creationDate', "actions"];

  dataSource!: MatTableDataSource<any>;

  loading = true;
  userName: string = '';

  customerName: string = '';
  creditAmount: number = 0;
  id: number = 0;

  constructor(
    private camundaService: ContractService,
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

  startProcess(contract: any) {
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
    this.camundaService.getContracts(this.userName).subscribe({
      next: value => {
        debugger
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
