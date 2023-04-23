import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Message } from '../models/message.model';
import { MessagesService } from '../services/messages.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-communications',
  templateUrl: './communications.component.html',
  styleUrls: ['./communications.component.css']
})
export class CommunicationsComponent implements OnInit, OnChanges {
  @Input() userUuid?: string|null = '';
  @Input() groupUuid?: string|null = '';
  @Input() iSubscribed: boolean = false;

  eventSource: EventSource|null = null;

  last10Messages: Message[] = [];
  behaviorSubject$: BehaviorSubject<Message>|null = null;

  constructor(private messagesService: MessagesService,) { }

  ngOnInit(): void {
    
  }

  private getLast10Messages(): void {
    if(!this.groupUuid){
      this.groupUuid = null;
    }
    this.messagesService.getLast10Messages(this.groupUuid)
    .subscribe(messages => (this.last10Messages = messages));
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
    if(this.userUuid && !this.iSubscribed){
      const emptyMessage: Message = {
        uuid: '',
        content: '',
        timestamp: 0
      };
      this.behaviorSubject$ = new BehaviorSubject<Message>(emptyMessage);
      this.eventSource = this.messagesService.subscribeUser(this.userUuid, this.behaviorSubject$);
      this.iSubscribed = true;
    } else {
      this.iSubscribed = false;
      this.closeExchanges();
    }
  }

  private closeExchanges(): void {
    this.eventSource?.close();
    this.eventSource = null;
    this.behaviorSubject$?.unsubscribe();
    this.behaviorSubject$ = null;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes && (changes['userUuid'] && changes['userUuid'].previousValue !== changes['userUuid'].currentValue ||
      changes['iSubscribed'] && changes['iSubscribed'].previousValue !== changes['iSubscribed'].currentValue)){
      this.subscribeUser();
    }

    this.watchMessagesChanges();
    this.getLast10Messages();
  }
}
