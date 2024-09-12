import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TransactionService } from '../transaction.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private transactionService: TransactionService, private router: Router) {}

  // login(): void {
  //   if (this.transactionService.login(this.username, this.password)) {
  //     this.router.navigate(['/transactions']);
  //   } else {
  //     alert('Invalid credentials');
  //   }
  // }

  login(): void {
    // Perform login logic, for example, by calling a service method
    if (this.username && this.password) {
      // Using the login method from TransactionService to authenticate the user
      if (this.transactionService.login(this.username, this.password)) {
        this.router.navigate(['/transactions']);  // Navigate to transactions if login is successful
      } else {
        alert('Invalid credentials');  // Handle login failure
      }
    } else {
      alert('Username and password are required!');  // Handle missing credentials
    }
  }
}
