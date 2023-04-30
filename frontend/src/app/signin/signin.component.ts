import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { User } from '../models/user.model';
import { AuthService } from '../services/auth.service';
import { AppState } from '../store/app.state';
import { UserAcions } from '../store/user/actions';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  signinForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
    password: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
  });
  constructor(private authService: AuthService,
    private store: Store<AppState>,
    private router: Router) { }

  ngOnInit(): void {
  }

  submit(): void {
    if(this.signinForm.valid){
      const request = this.signinForm.value;
      this.authService.signIn(request)
      .subscribe((user: User) => {
        this.store.dispatch(UserAcions.SetUserAction(user));
        this.store.dispatch(UserAcions.ConnectAction({isConnected: true}));
        this.router.navigate(['/']);
      })
      
    }
  }
}
