import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-add-group-modal',
  templateUrl: './add-group-modal.component.html',
  styleUrls: ['./add-group-modal.component.css']
})
export class AddGroupModalComponent implements OnInit {
  groupName = new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]);
  constructor(public modal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  saveGroup(): void {
    if(this.groupName.valid){
      const name = this.groupName.value;
      this.modal.close(name);
    }
    
  }
}
