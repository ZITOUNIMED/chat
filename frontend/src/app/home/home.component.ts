import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Message } from '../models/message.model';
import { User } from '../models/user.model';
import { MessagesService } from '../services/messages.service';
import { AppState } from '../store/states/app.state';
import { UserAcions } from '../store/states/user/actions';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  user$: Observable<User>;
  last10Messages: Message[] = [];
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
  }

  ngOnInit(): void {
    this.getLast10Messages();
    this.subscribeUser();
    this.watchMessagesChanges();
  }

  private watchMessagesChanges(): void {
    if(this.behaviorSubject$){
      this.behaviorSubject$.subscribe(message => {
        if(message && message.uuid){
          this.last10Messages.unshift(message);
          if(this.last10Messages.length>10){
            this.last10Messages.pop();
          }
        }
      });
    }
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

  private closeExchanges(): void {
    this.eventSource?.close();
    this.eventSource = null;
    this.behaviorSubject$?.unsubscribe();
    this.behaviorSubject$ = null;
  }

  private getLast10Messages(): void {
    this.messagesService.getLast10Messages()
    .subscribe(messages => (this.last10Messages = messages));
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
