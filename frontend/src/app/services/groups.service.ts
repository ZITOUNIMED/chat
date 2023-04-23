import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { AddGroup } from "../models/add-group.model";
import { GroupModel } from "../models/group.model";

@Injectable({
    providedIn: 'root'
})
export class GroupsService {
    url = environment.api_url + '/groups';
    constructor(private http: HttpClient) {
    }

    addGroup(request: AddGroup): Observable<any>{
        return this.http.post(this.url + '/add', request);
    }

    getUserGroups(userUuid: string): Observable<GroupModel[]> {
        return this.http.get<GroupModel[]>(this.url + `/user-groups/${userUuid}`);
    }
}