import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-adventure-details',
  templateUrl: './adventure-details.component.html',
  styleUrls: ['./adventure-details.component.scss']
})
export class AdventureDetailsComponent implements OnInit {
  newEntityForm!: FormGroup;
  @Input() entityRegistrationRequest!: any;
  @Output() addAdventureDetailsEvent = new EventEmitter();
  
  constructor() { }

  ngOnInit(): void {
  }

  addAdventureDetails(){
    
    this.addAdventureDetailsEvent.emit();
  }

}
