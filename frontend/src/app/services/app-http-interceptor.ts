import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { AppState } from "../store/states/app.state";

@Injectable({
    providedIn: 'root'
})
export class AppHttpInterceptor implements HttpInterceptor {
    token: string|undefined;
    constructor(private store: Store<AppState>) {
        this.store.select(state => state.user?.token)
        .subscribe(token => {
            this.token = token;
        });
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req.clone({setHeaders: {'Authorization': `Bearer ${this.token}`}}));
    }
}