import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  environment
} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  private baseUrl =
    `${environment.apiUrl}/api/expense`;

  constructor(
    private http: HttpClient
  ) {}

  // ✅ ADD EXPENSE

  addExpense(expense: any) {

    return this.http.post(

      this.baseUrl,

      expense
    );
  }

  // ✅ GET EXPENSES

  getExpenses() {

    return this.http.get(

      this.baseUrl
    );
  }
}