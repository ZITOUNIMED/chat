import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { AppState } from '../store/app.state';
import { UserAcions } from '../store/user/actions';
import { GroupModel } from '../models/group.model';
import { Message } from '../models/message.model';
import { MessagesService } from '../services/messages.service';
import { PostAcions } from '../store/post/actions';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  user$: Observable<User>;
  group$: Observable<GroupModel>;
  page: string = 'home';
  behaviorSubject$: BehaviorSubject<Message>|null = null;
  eventSource: EventSource|null = null;
  iSubscribed: boolean = false;

  constructor(private store: Store<AppState>,
    private messagesService: MessagesService,
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
        if(!groupState.uuid){
          this.page = 'home';
        }

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
    this.subscribeUser();
    this.watchMessagesChanges();
    this.watchSelectedGroupChanged();
  }

  private subscribeUser(): void {
    this.user$.subscribe(user => {
      if(user && user.uuid && !this.iSubscribed){
        const emptyMessage: Message = {
          uuid: '',
          content: '',
          timestamp: 0
        };
        this.behaviorSubject$ = new BehaviorSubject<Message>(emptyMessage);
        this.eventSource = this.messagesService.subscribeUser(user.uuid, this.behaviorSubject$);
        this.iSubscribed = true;
      } else {
        this.iSubscribed = false;
        this.closeExchanges();
      }
    })
  }

  private watchMessagesChanges(): void {
    if(this.behaviorSubject$){
      this.behaviorSubject$.subscribe(message => {
        if(message && message.uuid){
          this.store.dispatch(PostAcions.SetNewPostAction(message));
        }
      });
    }
  }

  private closeExchanges(): void {
    this.eventSource?.close();
    this.eventSource = null;
    this.behaviorSubject$?.unsubscribe();
    this.behaviorSubject$ = null;
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
      this.closeExchanges();
      this.store.dispatch(UserAcions.ResetUserAction());
      this.store.dispatch(UserAcions.ConnectAction({isConnected: false}));
      this.router.navigate(['/sign-in']);
    }
  }

}
