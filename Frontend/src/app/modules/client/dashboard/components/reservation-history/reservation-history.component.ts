import { AfterViewInit, Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import _ from 'lodash';
import DateUtil from 'src/app/core/utils/date.util';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { OptionsDialogComponent } from 'src/app/shared/components/options-dialog/options-dialog.component';
import { Reservation } from '../../../models/reservation.model';
import { ReservationService } from '../../../services/reservation.service';
import { RateExperienceDialogComponent } from '../rate-experience-dialog/rate-experience-dialog.component';
import { ReportDialogComponent } from '../report-dialog/report-dialog.component';

@Component({
  selector: 'db-reservation-history',
  templateUrl: './reservation-history.component.html',
  styleUrls: ['./reservation-history.component.scss']
})
export class ReservationHistoryComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['image', 'name', 'type', 'start', 'end', 'price', 'status', 'options'];
  dataSource!: MatTableDataSource<Reservation>;
  selectedOptionBoxId: number = -1;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChildren('optionBox') optionBox!: QueryList<ElementRef>;
  @ViewChildren('optionBtn') optionBtn!: QueryList<ElementRef>;

  constructor(private reservationService: ReservationService, private renderer: Renderer2, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.reservationService.getReservationHistoryList().subscribe(data => {
      this.dataSource =  new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.renderer.listen('body', 'click', (e: Event) => {
        for (let el of this.optionBox) {
          if (el.nativeElement.contains(e.target)) {
            return;
          }
        }
        for (let el of this.optionBtn) {
          if (el.nativeElement.contains(e.target)) {
            return;
          }
        }
        this.selectedOptionBoxId = -1;
      })
    });
  }

  ngAfterViewInit() {
    if(this.dataSource){
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    } 
  }

  getClass(status: string): string {
    if (status == "Active")
      return "lightblue";
    if (status == "Completed" || status == "Unrated")
      return "green";
    if (status == "Canceled")
      return "red";
    return "darkgrey";
  }

  getReservationType(type: string): string{
    if(type.includes("FishingLesson"))
      return "Adventure";
    else if(type.includes("Boat"))
      return "Boat";
    else if(type.includes("House"))
      return "House";
    else
      return "Unknown";
  }

  getTextClass(status: string): string {
    if (status == "Active")
      return "lightblue-text";
    if (status == "Completed" || status == "Unrated")
      return "green-text";
    if (status == "Canceled")
      return "red-text";
    return "grey-text";
  }

  selectOptionBox(id: number): void {
    if (id == this.selectedOptionBoxId)
      this.selectedOptionBoxId = -1;
    else
      this.selectedOptionBoxId = id;
  }

  isOptionsBoxVisible(id: number): boolean {
    if (id == this.selectedOptionBoxId)
      return true;
    else
      return false;
  }

  cancelReservation(reservationId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      title: "Cancel this reservation?",
      message: "Once you cancel this reservation there is no going back. You will lose your points if you proceed.",
      buttonNoText: "No",
      buttonYesText: "Yes",
    };
    const dialogRef = this.dialog.open(OptionsDialogComponent, dialogConfig);
    dialogRef.componentInstance.accept.subscribe(_ => {
      this.cancelReservationCallback(reservationId);
    })
  }

  cancelReservationCallback(reservationId: number) {
    this.reservationService.cancelReservation(reservationId).subscribe(
      data => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Reservation cancelled",
          message: "Reservation was successfuly cancelled.",
          buttonText: "Okay",
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      }
    );
  }

  canCancelReservation(id: number): boolean{
    let reservation = _.find(this.dataSource.data, function(res){return res.id == id});
    if(reservation){
      let start = new Date(reservation!.start)
      let today = new Date()
      if(DateUtil.getDifferenceInDays(start, today) < 3 || start < today)
        return false;
      return true;
    }
    return false;
  }

  reportSeller(reservation: Reservation){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reservation: reservation,
    };
    this.dialog.open(ReportDialogComponent, dialogConfig);
  }

  rateExperience(reservation: Reservation){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reservation: reservation,
    };
    this.dialog.open(RateExperienceDialogComponent, dialogConfig);
  }
}
