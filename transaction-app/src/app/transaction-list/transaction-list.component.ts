//import { Component } from '@angular/core';

//@Component({
// selector: 'app-transaction-list',
 // standalone: true,
 // imports: [],
 // templateUrl: './transaction-list.component.html',
 // styleUrl: './transaction-list.component.css'
//})
//export class TransactionListComponent {

//}
// src/app/transaction-list/transaction-list.component.ts

import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../transaction.service';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: any[] = [];
  page = 0;
  size = 10;
  totalPages = 0;
  customerId: string = '';
  accountNumber: string = '';
  description: string = '';

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.transactionService.getTransactions(this.page, this.size, this.customerId, this.accountNumber, this.description)
      .subscribe((data) => {
        this.transactions = data.content;
        this.totalPages = data.totalPages;
      });
  }

  // onPageChange(page: number): void {
  //   this.page = page;
  //   this.loadTransactions();
  // }

  onPageChange(event: any): void {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    this.loadTransactions();
  }

  onSearch(): void {
    this.page = 0; // Reset to the first page on search
    this.loadTransactions();
  }
}
