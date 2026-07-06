import { Component } from '@angular/core';

import { FormsModule } from '@angular/forms';

import {
  Router,
  RouterLink
} from '@angular/router';

import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',

  standalone: true,

  imports: [
    FormsModule,
    RouterLink
  ],

  templateUrl: './login.html',

  styleUrl: './login.css'
})
export class Login {

  email = '';

  password = '';

  constructor(

    private authService: Auth,

    private router: Router
  ) {}

  loginUser() {

    const credentials = {
  
      email: this.email,
  
      password: this.password
    };
  
    this.authService
      .login(credentials)
  
      .subscribe({
  
        next: (response: any) => {
  
          console.log(
            'LOGIN RESPONSE:',
            response
          );
  
          // ✅ RESPONSE IS ALREADY JWT STRING
  
          const token = response;
  
          // ✅ SAVE TOKEN
  
          this.authService.saveToken(
            token
          );
  
          console.log(
            'TOKEN SAVED:',
            localStorage.getItem('token')
          );
  
          alert(
            'Login Successful'
          );
  
          this.router.navigate([
            '/dashboard'
          ]);
        },
  
        error: (error: any) => {
  
          console.log(error);
  
          alert(
            'Invalid Credentials'
          );
        }
      });
  }
}