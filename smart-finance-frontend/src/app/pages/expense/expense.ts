import {
  Component,
  OnInit
} from '@angular/core';

import { FormsModule } from '@angular/forms';

import { CommonModule } from '@angular/common';

import { ExpenseService } from '../../services/expense-service';

import { DashboardRefreshService }
from '../../services/dashboard-refresh';

@Component({
  selector: 'app-expense',

  imports: [
    FormsModule,
    CommonModule
  ],

  templateUrl: './expense.html',

  styleUrl: './expense.css',
})
export class Expense implements OnInit {

  amount = 0;

  category = '';

  description = '';

  date = '';

  expenses: any[] = [];

  categories = [
    'FOOD',
    'TRAVEL',
    'SHOPPING',
    'BILLS',
    'ENTERTAINMENT',
    'HEALTH',
    'OTHER'
  ];

  constructor(

    private expenseService: ExpenseService,
  
    private dashboardRefresh:
      DashboardRefreshService
  
  ) {}

  ngOnInit(): void {

    this.loadExpenses();
    this.dashboardRefresh
  .triggerRefresh();
  }

  // ✅ ADD EXPENSE
  addExpense() {

    const expenseData = {

      amount: this.amount,

      category: this.category,

      description: this.description,

      date: this.date
    };

    this.expenseService
      .addExpense(expenseData)

      .subscribe({

        next: () => {

          alert(
            'Expense Added'
          );

          this.loadExpenses();

          this.amount = 0;
          this.category = '';
          this.description = '';
          this.date = '';
        },

        error: (error: any) => {

          console.log(error);

          alert(
            'Failed to Add Expense'
          );
        }
      });
  }

  // ✅ LOAD EXPENSES
  loadExpenses() {

    this.expenseService
      .getExpenses()

      .subscribe({

        next: (data: any) => {

          this.expenses = data;
        },

        error: (error: any) => {

          console.log(error);
        }
      });
  }
}