import { Component } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { Auth } from '../../services/auth';

@Component({
  selector: 'app-register',
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  name = '';
  email = '';
  password = '';

  constructor(
    private authService: Auth,
    private router: Router
  ) {}

  registerUser() {

    const user = {
      name: this.name,
      email: this.email,
      password: this.password
    };

    this.authService.register(user).subscribe({

      next: (response) => {

        console.log(response);

        alert('Registration Successful');

        this.router.navigate(['/login']);
      },

      error: (error) => {

        console.log(error);

        alert('Registration Failed');
      }
    });
  }
}