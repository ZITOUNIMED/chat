import { Component, EventEmitter, Output } from '@angular/core';
import { Observable, map } from 'rxjs';
import { User } from '../models/user.model';
import { AppState } from '../store/states/app.state';
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
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
  signOut(): void {
    this.signOutchange.emit(true);
  }

}
