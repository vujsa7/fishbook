import { Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { EntitySubscription } from '../../models/entity-subscription.model';
import { SubscriptionService } from '../../services/subscription.service';

@Component({
  selector: 'cl-subscribed-entities',
  templateUrl: './subscribed-entities.component.html',
  styleUrls: ['./subscribed-entities.component.scss']
})
export class SubscribedEntitiesComponent implements OnInit {

  displayedColumns: string[] = ['image', 'name', 'type', 'activeSpecialOffers', 'options'];
  dataSource!: MatTableDataSource<EntitySubscription>;
  selectedOptionsBoxId: number = -1;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChildren('optionBox') optionBox!: QueryList<ElementRef>;
  @ViewChildren('optionBtn') optionBtn!: QueryList<ElementRef>;

  constructor(private subscriptionService: SubscriptionService,  private renderer: Renderer2) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource(this.subscriptionService.getSubscriptionList());
    this.renderer.listen('body', 'click', (e:Event) => { 
      for(let el of this.optionBox){
        if(el.nativeElement.contains(e.target)){
          return;
        }
      }
      for(let el of this.optionBtn){
        if(el.nativeElement.contains(e.target)){
          return;
        }
      }
      this.selectedOptionsBoxId = -1;
    })
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  selectOptionBox(id: number): void{
    this.selectedOptionsBoxId = id;
    console.log("SELECTED")
  }

  isOptionsBoxVisible(id: number): boolean{
    if(id == this.selectedOptionsBoxId)
      return true;
    else
      return false;
  }

}
