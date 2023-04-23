import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Observable, map } from 'rxjs';
import { AppState } from '../store/states/app.state';
import { Store } from '@ngrx/store';
import { User } from '../models/user.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddGroupModalComponent } from '../modals/add-group-modal/add-group-modal.component';
import { GroupsService } from '../services/groups.service';
import { AddGroup } from '../models/add-group.model';
import { GroupModel } from '../models/group.model';
import { GroupAcions } from '../store/states/group/actions';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  user$: Observable<User>;
  userUuid: string = '';
  @Output() signOutchange = new EventEmitter<boolean>();
  groups: GroupModel[] = [];
  selectedGroupUuid: string = '';

  constructor(private store: Store<AppState>, 
    private modalService: NgbModal,
    private groupsService: GroupsService) { 
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
    this.user$.subscribe(user => {
      this.userUuid = user.uuid;
      if(this.userUuid){
        this.getUserGroups();
      }
    })
  }

  getUserGroups(): void {
    this.groupsService.getUserGroups(this.userUuid)
    .subscribe(groups => {
      this.groups = groups;
    })
  }

  openAddGroupModal(): void {
    const modalRef  = this.modalService.open(AddGroupModalComponent);
    modalRef.result.then((name) => {
      if(name){
        this.saveGroup(name);
      }
    }, (reason) => {
      console.log(reason);
    });
  }

  saveGroup(name: string): void {
    const resuest: AddGroup = {
      name,
      userUuid: this.userUuid
    };

    this.groupsService.addGroup(resuest)
    .subscribe(() => {
      this.getUserGroups();
    })
  }

  openGroup(group: GroupModel): void {
    this.selectedGroupUuid = group.uuid;
    this.store.dispatch(GroupAcions.SetGroupAction(group));
  }

  signOut(): void {
    this.signOutchange.emit(true);
  }
}
