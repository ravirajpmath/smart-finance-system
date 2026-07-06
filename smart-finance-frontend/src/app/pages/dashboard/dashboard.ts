
import {
  Component,
  OnInit
} from '@angular/core';

import {
  Router,
  RouterLink
} from '@angular/router';

import { CommonModule } from '@angular/common';

import { BaseChartDirective } from 'ng2-charts';

import {
  Chart,
  registerables,
  ChartConfiguration,
  ChartData
} from 'chart.js';

Chart.register(...registerables);

import { Auth } from '../../services/auth';
import { BudgetService } from '../../services/budget-service';
import { IncomeService } from '../../services/income-service';
import { ExpenseService } from '../../services/expense-service';
import { DashboardRefreshService } from '../../services/dashboard-refresh';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    BaseChartDirective
  ],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class Dashboard implements OnInit {

  budgetData: any = null;

  recentIncome: any[] = [];

  recentExpenses: any[] = [];

  // =========================
  // BAR CHART
  // =========================

  barChartType: 'bar' = 'bar';

  barChartData: ChartData<'bar'> = {
    labels: ['Income', 'Expense', 'Remaining'],
    datasets: [
      {
        data: [0, 0, 0],
        label: 'Financial Overview',
        backgroundColor: [
          '#22c55e',
          '#ef4444',
          '#3b82f6'
        ]
      }
    ]
  };

  barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false
  };

  // =========================
  // PIE CHART
  // =========================

  pieChartType: any = 'pie';

  pieChartLabels: string[] = [];

  pieChartDatasets = [
    {
      data: [] as number[],
      backgroundColor: [
        '#ef4444',
        '#3b82f6',
        '#22c55e',
        '#f59e0b',
        '#8b5cf6',
        '#ec4899',
        '#14b8a6',
        '#f97316'
      ]
    }
  ];

  pieChartOptions: any = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    }
  };

  constructor(
    private authService: Auth,
    private router: Router,
    private budgetService: BudgetService,
    private incomeService: IncomeService,
    private expenseService: ExpenseService,
    private dashboardRefresh: DashboardRefreshService
  ) {}

  ngOnInit(): void {

    this.loadDashboard();

    this.loadTransactions();

    this.dashboardRefresh
      .refreshDashboard$
      .subscribe(() => {

        this.loadDashboard();

        this.loadTransactions();
      });
  }

  // =========================
  // LOAD DASHBOARD
  // =========================

  loadDashboard() {

    this.budgetService
      .calculateBudget()
      .subscribe({

        next: (data: any) => {

          console.log('DASHBOARD DATA:', data);

          this.budgetData = data;

          // =========================
          // BAR CHART UPDATE
          // =========================

          this.barChartData = {
            labels: [
              'Income',
              'Expense',
              'Remaining'
            ],
            datasets: [
              {
                data: [
                  data.income || 0,
                  data.actualSpending || 0,
                  data.remaining || 0
                ],
                label: 'Financial Overview',
                backgroundColor: [
                  '#22c55e',
                  '#ef4444',
                  '#3b82f6'
                ]
              }
            ]
          };

          // =========================
          // PIE CHART FROM BACKEND
          // =========================

          const categories = data.categoryBreakdown || {};

          console.log('CATEGORY BREAKDOWN:', categories);

          // IF BACKEND RETURNS DATA
          if (Object.keys(categories).length > 0) {

            this.updatePieChart(categories);

          } else {

            console.log(
              'Backend categoryBreakdown empty. Falling back to expense aggregation.'
            );

            // FALLBACK FROM EXPENSE LIST
            this.loadPieChartFromExpenses();
          }
        },

        error: (error: any) => {
          console.log(error);
        }
      });
  }

  // =========================
  // PIE CHART FALLBACK
  // =========================

  loadPieChartFromExpenses() {

    this.expenseService
      .getExpenses()
      .subscribe({

        next: (expenses: any) => {

          const categoryTotals: any = {};

          expenses.forEach((expense: any) => {

            const category = expense.category || 'Other';

            const amount = Number(expense.amount) || 0;

            if (!categoryTotals[category]) {
              categoryTotals[category] = 0;
            }

            categoryTotals[category] += amount;
          });

          console.log(
            'GENERATED CATEGORY TOTALS:',
            categoryTotals
          );

          this.updatePieChart(categoryTotals);
        },

        error: (error: any) => {
          console.log(error);
        }
      });
  }

  // =========================
  // UPDATE PIE CHART
  // =========================

  updatePieChart(categoryData: any) {

    const labels = Object.keys(categoryData);

    const values = Object.values(categoryData) as number[];

    console.log('PIE LABELS:', labels);
    console.log('PIE VALUES:', values);

    this.pieChartLabels = [...labels];

    this.pieChartDatasets = [
      {
        data: [...values],
        backgroundColor: [
          '#ef4444',
          '#3b82f6',
          '#22c55e',
          '#f59e0b',
          '#8b5cf6',
          '#ec4899',
          '#14b8a6',
          '#f97316'
        ]
      }
    ];
  }

  // =========================
  // LOAD TRANSACTIONS
  // =========================

  loadTransactions() {

    // INCOME

    this.incomeService
      .getIncome()
      .subscribe({

        next: (data: any) => {
          this.recentIncome = data.slice(-3).reverse();
        },

        error: (error: any) => {
          console.log(error);
        }
      });

    // EXPENSES

    this.expenseService
      .getExpenses()
      .subscribe({

        next: (data: any) => {
          this.recentExpenses = data.slice(-3).reverse();
        },

        error: (error: any) => {
          console.log(error);
        }
      });
  }

  // =========================
  // LOGOUT
  // =========================

  logout() {

    this.authService.logout();

    alert('Logged Out');

    this.router.navigate(['/login']);
  }
}


