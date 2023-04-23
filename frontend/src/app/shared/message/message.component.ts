import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { PostMessage } from 'src/app/models/post-message';
import { MessagesService } from 'src/app/services/messages.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  messageCtrl = new FormControl('', [Validators.required, Validators.maxLength(255)]);
  @Input() userUuid?: string|null = '';
  @Input() groupUuid?: string|null = '';
  constructor(private messagesService: MessagesService) { }

  ngOnInit(): void {
  }

  postMessage(): void {
    if(this.messageCtrl.valid){
      const message = this.messageCtrl.value;
      const request: PostMessage = {
        message,
        userUuid: this.userUuid,
        groupUuid: this.groupUuid
      };

      this.messagesService.postMessage(request).subscribe(()=> {
        this.messageCtrl.setValue('');
      });
    }
  }
}
