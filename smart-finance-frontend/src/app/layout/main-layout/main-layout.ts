import { Component } from '@angular/core';

import { Router, RouterLink, RouterOutlet } from '@angular/router';

import { CommonModule } from '@angular/common';

import { Auth } from '../../services/auth';

@Component({
  selector: 'app-main-layout',

  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    RouterOutlet
  ],

  templateUrl: './main-layout.html',

  styleUrls: ['./main-layout.css']
})
export class MainLayout {

  constructor(
    private authService: Auth,
    private router: Router
  ) {}

  logout() {

    this.authService.logout();

    alert('Logged Out');

    this.router.navigate(['/login']);
  }
}