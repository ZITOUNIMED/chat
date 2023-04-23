import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { AppState } from '../store/states/app.state';
import { UserAcions } from '../store/states/user/actions';
import { GroupModel } from '../models/group.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  user$: Observable<User>;
  group$: Observable<GroupModel>;
  page: string = 'home';
  
  iSubscribed: boolean = false;

  constructor(private store: Store<AppState>,
    private router: Router) { 
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

    this.group$ = this.store.select(state => state.group).pipe(
      map(groupState => {
        const group: GroupModel = {
          uuid: groupState.uuid,
          name: groupState.name,
          adminUuid: groupState.adminUuid,
          creatorUuid: groupState.creatorUuid,
          createdAt: groupState.createdAt,
          users: groupState.users,
        };

        return group;
      })
    );
  }

  ngOnInit(): void {
    this.watchSelectedGroupChanged();
  }

  private watchSelectedGroupChanged(): void {
    this.group$.subscribe(group => {
      if(group && group.uuid){
        this.page = 'group';
      } else {
        this.page = 'home';
      }
    })
  }

  

  

  signOut(isSignOut: boolean): void {
    if(isSignOut){
      this.iSubscribed = false;
      this.store.dispatch(UserAcions.ResetUserAction());
      this.store.dispatch(UserAcions.ConnectAction({isConnected: false}));
      this.router.navigate(['/sign-in']);
    }
  }

}
