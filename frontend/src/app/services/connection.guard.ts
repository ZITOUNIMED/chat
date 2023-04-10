import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Store } from "@ngrx/store";
import { Observable, switchMap } from "rxjs";
import { AppState } from "../store/states/app.state";

@Injectable({
    providedIn: 'root'
})
export class ConnectionGuard implements CanActivate {
    isConnected: boolean = false;
    constructor(private store: Store<AppState>, private router: Router) {
        this.store.select(state => state.user?.isConnected)
        .subscribe(isConnected => {
            this.isConnected = isConnected;
        });
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> {
        if(!this.isConnected){
            this.router.navigate(['/sign-in']);
        }

        return true;
    }
}