// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-transaction-edit',
//   standalone: true,
//   imports: [],
//   templateUrl: './transaction-edit.component.html',
//   styleUrl: './transaction-edit.component.css'
// })
// export class TransactionEditComponent {

// }
// src/app/transaction-edit/transaction-edit.component.ts

import { Component, Input, OnInit } from '@angular/core';
import { TransactionService } from '../transaction.service';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-transaction-edit',
  templateUrl: './transaction-edit.component.html',
  styleUrls: ['./transaction-edit.component.css']
})
export class TransactionEditComponent implements OnInit {
  @Input() transactionId: number | undefined;
  newDescription: string = '';

  constructor(private transactionService: TransactionService, private router: Router) {}

  ngOnInit(): void {
    if (!this.transactionId) {
      alert('Transaction ID is required!');
      this.router.navigate(['/']);
    }
  }

  updateTransaction(): void {
    if (this.transactionId && this.newDescription) {
      this.transactionService.updateTransaction(this.transactionId, this.newDescription).subscribe(
        () => {
          alert('Transaction updated successfully!');
          this.router.navigate(['/']);
        },
        (error) => {
          alert('Failed to update transaction: ' + error.message);
        }
      );
    }
  }
}
