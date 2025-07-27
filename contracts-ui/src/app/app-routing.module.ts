import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {AuthGuard} from "./guard/auth.guard";
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/auth/login/login.component";
import {RegisterComponent} from "./pages/auth/register/register.component";
import {
  ContractsComponent
} from "./pages/contracts/contracts/contracts.component";
import {
  ContractEditorComponent
} from "./pages/contracts/contract-editor/contract-editor.component";
import {UserTaskHistoryComponent} from "./pages/history/user-task-his/user-task-history.component";

const routes: Routes = [
  {
    path: '',
    component: AppComponent,
    canActivate: [AuthGuard]
  },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'contracts', component: ContractsComponent },
  { path: 'new-contract', component: ContractEditorComponent },
  // { path: 'process/:id', component: ProcessDetailComponent },
  // { path: 'user-task-history', component: UserTaskHistoryComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
