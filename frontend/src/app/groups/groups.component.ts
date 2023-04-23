import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  @Input() userUuid?: string|null = '';
  @Input() groupUuid?: string|null = '';
  @Input() iSubscribed: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

}
