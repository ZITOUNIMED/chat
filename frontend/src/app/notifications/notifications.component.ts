import { Component, OnInit } from '@angular/core';
import { NotificationModel } from '../models/notification.model';
import { Observable, map } from 'rxjs';
import { Store } from '@ngrx/store';
import { AppState } from '../store/app.state';
import { PostState } from '../store/post/post.state';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  notifications$: Observable<NotificationModel[]>;
  constructor(private store: Store<AppState>,) {
    this.notifications$ = this.store.select(state => state.post).pipe(
      map((post: PostState) => {
        const notifications: NotificationModel[] = post.notifications;
        return notifications;
      })
    );
  }

  ngOnInit(): void {
  }

}
