import { Routes } from '@angular/router';

import { Login } from './pages/login/login';

import { Register } from './pages/register/register';

import { Dashboard } from './pages/dashboard/dashboard';

import { Income } from './pages/income/income';

import { Expense } from './pages/expense/expense';

import { BudgetComponent } from './pages/budget/budget';

import { MainLayout } from './layout/main-layout/main-layout';

import { authGuard } from './guards/auth-guard';

export const routes: Routes = [

  // LOGIN PAGE

  {
    path: 'login',
    component: Login
  },

  // REGISTER PAGE

  {
    path: 'register',
    component: Register
  },

  // MAIN APP LAYOUT

  {
    path: '',

    component: MainLayout,
    canActivate: [authGuard],

    children: [

      // DEFAULT DASHBOARD

      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },

      {
        path: 'dashboard',
        component: Dashboard
      },

      {
        path: 'income',
        component: Income
      },

      {
        path: 'expense',
        component: Expense
      },

      {
        path: 'budget',
        component: BudgetComponent
      }
    ]
  },

  // FALLBACK

  {
    path: '**',
    redirectTo: 'dashboard'
  }
];