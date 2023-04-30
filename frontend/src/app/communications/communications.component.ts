import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Message } from '../models/message.model';
import { MessagesService } from '../services/messages.service';
import { Store } from '@ngrx/store';
import { AppState } from '../store/app.state';
import { PostAcions } from '../store/post/actions';
import { Observable, map } from 'rxjs';
import { PostState } from '../store/post/post.state';
import { PostInitMessage } from '../store/payloads/post-init-messages.model';

@Component({
  selector: 'app-communications',
  templateUrl: './communications.component.html',
  styleUrls: ['./communications.component.css']
})
export class CommunicationsComponent implements OnChanges {
  @Input() userUuid?: string|null = '';
  @Input() groupUuid?: string|null = '';
  @Input() iSubscribed: boolean = false;
  last10Messages$: Observable< Message[]>;

  constructor(private messagesService: MessagesService,
    private store: Store<AppState>,) { 
      this.last10Messages$ = this.store.select(state => state.post).pipe(
        map((post: PostState) => {
          const last10Messages: Message[] = [
            ...post.generalMessages,
            ...post.selectedGroupMessages
          ]
          return last10Messages;
        })
      );
    }

  private getLast10Messages(): void {
    if(!this.groupUuid){
      this.groupUuid = null;
    }
    this.messagesService.getLast10Messages(this.groupUuid)
    .subscribe(messages => {
      const payload: PostInitMessage = {
        groupUuid: this.groupUuid? this.groupUuid : null,
        messages
      }
      this.store.dispatch(PostAcions.InitMessagesAction(payload));
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes && changes['groupUuid'] && 
        changes['groupUuid'].previousValue !== changes['groupUuid'].currentValue){
        this.getLast10Messages();
        this.store.dispatch(PostAcions.ResetPostsAction());
    }
  }
}
