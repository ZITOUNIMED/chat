import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { User } from '../models/user.model';
import { AuthService } from '../services/auth.service';
import { AppState } from '../store/app.state';
import { UserAcions } from '../store/user/actions';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
    confirmPassword: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
  });
  constructor(private authService: AuthService,
    private store: Store<AppState>,
    private router: Router) { }

  ngOnInit(): void {
    const validatorPasswords: ValidatorFn = _ => {
      const password = this.signupForm.get('password')?.value;
      const confirmPassword = this.signupForm.get('confirmPassword')?.value;
      if(password != confirmPassword) {
        return {
          passwordsDifferent: true
        };
      }
      return null;
    };
    this.signupForm.addValidators(validatorPasswords);
  }

  submit(): void {
    if(this.signupForm.valid){
      const request = this.signupForm.value;
      this.authService.signUp(request)
      .subscribe((user: User) => {
        this.store.dispatch(UserAcions.SetUserAction(user));
        this.store.dispatch(UserAcions.ConnectAction({isConnected: true}));
        this.router.navigate(['/']);
      })
      
    }
  }

}
