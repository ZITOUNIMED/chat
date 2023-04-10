import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

import { SignIn } from "../models/sign-in.model";
import { SignUp } from "../models/sign-up.model";
import { User } from "../models/user.model";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    url = environment.api_url + '/auth';

    constructor(private http: HttpClient) {}

    signUp(request: SignUp): Observable<User>{
        return this.http.post<User>(this.url + '/sign-up', request);
    }

    signIn(request: SignIn): Observable<User>{
        return this.http.post<User>(this.url + '/sign-in', request);
    }
}