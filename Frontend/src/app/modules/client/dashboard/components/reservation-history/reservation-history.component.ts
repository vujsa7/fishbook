import { AfterViewInit, Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import _ from 'lodash';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { OptionsDialogComponent } from 'src/app/shared/components/options-dialog/options-dialog.component';
import { ReservationHistory } from '../../../models/reservation-history.model';
import { ReservationService } from '../../../services/reservation.service';
import { RateExperienceDialogComponent } from '../rate-experience-dialog/rate-experience-dialog.component';
import { ReportDialogComponent } from '../report-dialog/report-dialog.component';

@Component({
  selector: 'db-reservation-history',
  templateUrl: './reservation-history.component.html',
  styleUrls: ['./reservation-history.component.scss']
})
export class ReservationHistoryComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['image', 'name', 'type', 'started', 'ended', 'price', 'status', 'options'];
  dataSource!: MatTableDataSource<ReservationHistory>;
  selectedOptionBoxId: number = -1;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChildren('optionBox') optionBox!: QueryList<ElementRef>;
  @ViewChildren('optionBtn') optionBtn!: QueryList<ElementRef>;

  constructor(private reservationService: ReservationService, private renderer: Renderer2, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource(this.reservationService.getReservationHistoryList());
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
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
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
      yesBtnCallback: this.cancelReservationCallback
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
      let start = new Date(reservation!.started)
      let today = new Date()
      if(this.getDifferenceInDays(start, today) < 3 || start < today)
        return false;
      return true;
    }
    return false;
  }

  getDifferenceInDays(date1: any, date2: any) {
    const diffInMs = Math.abs(date2 - date1);
    return diffInMs / (1000 * 60 * 60 * 24);
  }

  reportSeller(reservation: ReservationHistory){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reservation: reservation,
    };
    this.dialog.open(ReportDialogComponent, dialogConfig);
  }

  rateExperience(reservation: ReservationHistory){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      reservation: reservation,
    };
    this.dialog.open(RateExperienceDialogComponent, dialogConfig);
  }
}
