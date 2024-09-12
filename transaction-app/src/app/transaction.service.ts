// src/app/transaction.service.ts

import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private apiUrl = 'http://localhost:8080/api/transactions';

  constructor(private http: HttpClient) {}

  private createBasicAuthHeader(): HttpHeaders {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    const authHeader = 'Basic ' + btoa(`${username}:${password}`);
    return new HttpHeaders({ 'Authorization': authHeader });
  }

  // Retrieve transactions with pagination and search
  getTransactions(
    page: number,
    size: number,
    customerId?: string,
    accountNumber?: string,
    description?: string
  ): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (customerId) params = params.set('customerId', customerId);
    if (accountNumber) params = params.set('accountNumber', accountNumber);
    if (description) params = params.set('description', description);

    return this.http.get(this.apiUrl, { params });
  }

  // Update transaction description
  updateTransaction(id: number, newDescription: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, newDescription, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  login(username: string, password: string): boolean {
    // Store credentials in local storage to be used in the basic auth header
    localStorage.setItem('username', username);
    localStorage.setItem('password', password);
    return true;  // Normally, you would verify credentials with the backend
  }

  logout(): void {
    localStorage.removeItem('username');
    localStorage.removeItem('password');
  }
}
