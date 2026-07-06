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
export class IncomeService {

  private baseUrl =
    `${environment.apiUrl}/api/income`;

  constructor(
    private http: HttpClient
  ) {}

  // ✅ ADD INCOME

  addIncome(income: any) {

    return this.http.post(

      this.baseUrl,

      income
    );
  }

  // ✅ GET INCOME

  getIncome() {

    return this.http.get(

      this.baseUrl
    );
  }
}