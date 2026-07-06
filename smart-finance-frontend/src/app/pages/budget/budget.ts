import {
  Component,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { BudgetService } from '../../services/budget-service';

@Component({
  selector: 'app-budget',

  standalone: true,

  imports: [CommonModule],

  templateUrl: './budget.html',

  styleUrls: ['./budget.css']
})
export class BudgetComponent {

  budgetData: any = null;

  loading = true;

  constructor(
    private budgetService: BudgetService,
    private cdr: ChangeDetectorRef
  ) {

    this.loadBudget();
  }

  loadBudget() {

    this.budgetService
      .calculateBudget()

      .subscribe({

        next: (data: any) => {

          console.log(
            'BUDGET RESPONSE:',
            data
          );

          this.budgetData = data;

          this.loading = false;

          // ✅ FORCE UI UPDATE
          this.cdr.detectChanges();
        },

        error: (error: any) => {

          console.log(error);

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }
}