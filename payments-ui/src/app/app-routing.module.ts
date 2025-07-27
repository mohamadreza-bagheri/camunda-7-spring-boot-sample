import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {AuthGuard} from "./guard/auth.guard";
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/auth/login/login.component";
import {RegisterComponent} from "./pages/auth/register/register.component";
import {
  PaymentsComponent
} from "./pages/payments/payments/payments.component";
import {
  PaymentEditorComponent
} from "./pages/payments/payment-editor/payment-editor.component";

const routes: Routes = [
  {
    path: '',
    component: AppComponent,
    canActivate: [AuthGuard]
  },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'payments', component: PaymentsComponent },
  { path: 'new-payment', component: PaymentEditorComponent },
  // { path: 'process/:id', component: ProcessDetailComponent },
  // { path: 'user-task-history', component: UserTaskHistoryComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
