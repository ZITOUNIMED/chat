import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Observable, map } from 'rxjs';
import { AppState } from '../store/states/app.state';
import { Store } from '@ngrx/store';
import { User } from '../models/user.model';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  user$: Observable<User>;
  @Output() signOutchange = new EventEmitter<boolean>();

  constructor(private store: Store<AppState>) { 
    this.user$ = this.store.select(state => state.user).pipe(
      map(userState => {
        const user: User = {
          username: userState.username,
          name: userState.name,
          uuid: userState.uuid
        };

        return user;
      })
    );
  }

  ngOnInit(): void {
  }

  signOut(): void {
    this.signOutchange.emit(true);
  }
}
