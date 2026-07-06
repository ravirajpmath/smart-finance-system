import {
  Component,
  OnInit
} from '@angular/core';

import { FormsModule } from '@angular/forms';

import { CommonModule } from '@angular/common';

import { IncomeService } from '../../services/income-service';

import { DashboardRefreshService }
from '../../services/dashboard-refresh';

@Component({
  selector: 'app-income',

  imports: [
    FormsModule,
    CommonModule
  ],

  templateUrl: './income.html',

  styleUrl: './income.css',
})
export class Income implements OnInit {

  amount = 0;

  month = '';

  year = 2026;

  source = '';

  incomes: any[] = [];

  constructor(

    private incomeService: IncomeService,
  
    private dashboardRefresh:
      DashboardRefreshService
  
  ) {}

  ngOnInit(): void {

    this.loadIncome();
    this.dashboardRefresh
  .triggerRefresh();
  }

  

  addIncome() {

    const incomeData = {

      amount: this.amount,

      month: this.month,

      year: this.year,

      source: this.source
    };

    this.incomeService
      .addIncome(incomeData)

      .subscribe({

        next: () => {

          alert('Income Added');

          this.loadIncome();

          this.amount = 0;
          this.month = '';
          this.year = 2026;
          this.source = '';
        },

        error: (error: any) => {

          console.log(error);

          alert(
            'Failed to Add Income'
          );
        }
      });
  }

  loadIncome() {

    this.incomeService
      .getIncome()

      .subscribe({

        next: (data: any) => {

          this.incomes = data;
        },

        error: (error: any) => {

          console.log(error);
        }
      });
  }
}