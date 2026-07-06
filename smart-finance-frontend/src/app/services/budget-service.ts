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
export class BudgetService {

  private baseUrl =
    `${environment.apiUrl}/api/budget`;

  constructor(
    private http: HttpClient
  ) {}

  // ✅ CALCULATE BUDGET

  calculateBudget() {

    return this.http.post(

      this.baseUrl,

      {}
    );
  }
}