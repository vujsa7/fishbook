import { Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { OptionsDialogComponent } from 'src/app/shared/components/options-dialog/options-dialog.component';
import { EntitySubscription } from '../../models/entity-subscription.model';
import { SubscriptionService } from '../../services/subscription.service';

@Component({
  selector: 'db-subscribed-entities',
  templateUrl: './subscribed-entities.component.html',
  styleUrls: ['./subscribed-entities.component.scss']
})
export class SubscribedEntitiesComponent implements OnInit {

  displayedColumns: string[] = ['image', 'name', 'type', 'activeSpecialOffers', 'options'];
  dataSource!: MatTableDataSource<EntitySubscription>;
  selectedOptionBoxId: number = -1;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChildren('optionBox') optionBox!: QueryList<ElementRef>;
  @ViewChildren('optionBtn') optionBtn!: QueryList<ElementRef>;

  constructor(private subscriptionService: SubscriptionService, private renderer: Renderer2, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataSource = new MatTableDataSource(this.subscriptionService.getSubscriptionList());
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

  unsubscribe(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      title: "Unsubscribe?",
      message: "Do you really want to unsubscribe from receiving special offers notifications?",
      buttonNoText: "No",
      buttonYesText: "Yes",
      yesBtnCallback: this.unsubscribeCallback
    };
    const dialogRef = this.dialog.open(OptionsDialogComponent, dialogConfig);
    dialogRef.componentInstance.accept.subscribe(_ => {
      this.unsubscribeCallback(id);
    })
  }

  unsubscribeCallback(id: number) {
    this.subscriptionService.unsubscribe(id).subscribe(
      data => {
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = {
          title: "Unsubscribed",
          message: "You have succesfully unsubscribed. You won't receive notifications from this subscription anymore.",
          buttonText: "Okay",
        };
        this.dialog.open(InfoDialogComponent, dialogConfig);
      }
    );
  }

}
