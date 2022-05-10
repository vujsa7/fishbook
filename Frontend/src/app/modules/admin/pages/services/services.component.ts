import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AdventureService } from 'src/app/shared/services/adventure.service';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {
  selectedButton: string = 'boats';
  entities: any;

  constructor(private adventureService: AdventureService, private toastr: ToastrService) { }

  ngOnInit(): void {
  }
  
  selectButton(selectedButton: string): void {
    this.selectedButton = selectedButton;
    this.getEntities(selectedButton);
  }

  getEntities(type: string) {
    if(type == 'adventures'){
      this.adventureService.getAllAdventures().subscribe(data => {
        this.entities = data;
      }, error => {
        this.toastr.error("Error loading entities");
      })
    }
    if(type == 'boats'){
      this.entities = [];
    }
    if(type == 'houses'){
      this.entities = [];
    }
  }

}
