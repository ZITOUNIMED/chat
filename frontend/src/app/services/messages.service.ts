import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Message } from "../models/message.model";
import { PostMessage } from "../models/post-message";
import { Store } from "@ngrx/store";
import { AppState } from "../store/states/app.state";

@Injectable({
    providedIn: 'root'
})
export class MessagesService {
    url = environment.api_url + '/messages';
    token: string|undefined;
    constructor(private http: HttpClient, private store: Store<AppState>) {
      this.store.select(state => state.user?.token)
      .subscribe(token => {
          this.token = token;
      });
    }

    postMessage(request: PostMessage): Observable<any>{
        return this.http.post(this.url + '/post', request);
    }

    getLast10Messages(): Observable<Message[]> {
        return this.http.get<Message[]>(this.url + '/last-10');
    }

    subscribeUser(uuid: string, behaviorSubject$: BehaviorSubject<Message>): EventSource {
        const eventSource = new EventSource(this.url+`/subscribe-user/${uuid}?token=${this.token}`, { withCredentials: true });
        eventSource.addEventListener('message', message => {
            const data: Message = JSON.parse(message.data);
            behaviorSubject$.next(data);
        });

        return eventSource;
    }
}