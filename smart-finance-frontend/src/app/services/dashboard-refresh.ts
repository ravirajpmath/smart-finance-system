import { Injectable } from '@angular/core';

import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardRefreshService {

  private refreshDashboardSource =
    new Subject<void>();

  refreshDashboard$ =
    this.refreshDashboardSource.asObservable();

  triggerRefresh() {

    this.refreshDashboardSource.next();
  }
}