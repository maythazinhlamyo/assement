// src/app/app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TransactionListComponent } from './transaction-list/transaction-list.component';
import { TransactionEditComponent } from './transaction-edit/transaction-edit.component';

import { LoginComponent } from './login/login.component';

// const routes: Routes = [
    
//   { path: '', component: TransactionListComponent },
//   { path: 'edit/:id', component: TransactionEditComponent }
// ];

const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'transactions', component: TransactionListComponent },
    { path: 'edit-transaction/:id', component: TransactionEditComponent },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

