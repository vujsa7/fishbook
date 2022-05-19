import { AfterViewInit, Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ReservationHistory } from '../../models/reservation-history.model';
import { ReservationHistoryService } from '../../services/reservation-history.service';

@Component({
  selector: 'cl-reservation-history',
  templateUrl: './reservation-history.component.html',
  styleUrls: ['./reservation-history.component.scss']
})
export class ReservationHistoryComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['image', 'name', 'type', 'started', 'ended', 'price', 'status', 'options'];
  dataSource!: MatTableDataSource<ReservationHistory>;
  selectedOptionsBoxId: number = -1;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChildren('optionBox') optionBox!: QueryList<ElementRef>;
  @ViewChildren('optionBtn') optionBtn!: QueryList<ElementRef>;

  constructor(private reservationHistoryService: ReservationHistoryService, private renderer: Renderer2) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource(this.reservationHistoryService.getReservationHistoryList());
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

  getClass(status: string): string{
    if(status=="Active")
      return "lightblue";
    if(status=="Completed")
      return "green";
    if(status=="Canceled")
      return "red";
    return "darkgrey";
  }

  getTextClass(status: string): string{
    if(status=="Active")
      return "lightblue-text";
    if(status=="Completed")
      return "green-text";
    if(status=="Canceled")
      return "red-text";
    return "grey-text";
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
