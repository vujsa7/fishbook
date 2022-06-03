import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router, Event, NavigationEnd, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { AvailabilityDialogComponent } from 'src/app/modules/seller/components/availability-dialog/availability-dialog.component';
import { ReservationDialogComponent } from 'src/app/modules/seller/components/reservation-dialog/reservation-dialog.component';
import { SpecialOfferDialogComponent } from 'src/app/modules/seller/components/special-offer-dialog/special-offer-dialog.component';
import { EntityDetailsService } from '../../services/entity-details.service';

@Component({
  selector: 'app-entity-details',
  templateUrl: './entity-details.component.html',
  styleUrls: ['./entity-details.component.scss']
})
export class EntityDetailsComponent implements OnDestroy, OnInit {

  entityType: string = "";
  entityDetails!: any;
  ownerUsername: string = "";
  loggedInUserUsername: string = "";
  private routerSub: Subscription;

  constructor(private router: Router, private route: ActivatedRoute, private entityDetailsService: EntityDetailsService, private authService: AuthService, private dialog: MatDialog) {
    this.routerSub = router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        if(this.router.url.includes('houses')){
          this.entityType = "house";
        } else if(this.router.url.includes('boats')){
          this.entityType = "boat";
        } else if(this.router.url.includes('adventures')){
          this.entityType = "adventure";
        }
        this.fetchEntitiyDetails();
      }
    });
  }
  ngOnInit(): void {
    this.loggedInUserUsername = this.authService.getTokenUsername();
  }

  fetchEntitiyDetails() {
    let entityId = this.route.snapshot.params['id'];
    if(this.entityType == 'house')
      this.entityDetailsService.fetchHouseDetails(entityId).subscribe(data => {
        this.entityDetails = data;
        this.ownerUsername = this.entityDetails.sellerEmail;
      });
    else if(this.entityType == 'boat'){
      this.entityDetailsService.fetchBoatDetails(entityId).subscribe(data => {
        this.entityDetails = data;
        this.ownerUsername = this.entityDetails.sellerEmail;
      });
    } else if(this.entityType == 'adventure'){
      this.entityDetailsService.fetchAdventureDetails(entityId).subscribe(data => {
        this.entityDetails = data;
        this.ownerUsername = this.entityDetails.sellerEmail;
      });
    }
  }

  setAvailability() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      entityType: this.entityType,
      entityId: this.entityDetails.id
    }      
    this.dialog.open(AvailabilityDialogComponent, dialogConfig);
  }

  createSpecialOffer() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      entityId: this.entityDetails.id,
      entityType: this.entityType
    }      
    this.dialog.open(SpecialOfferDialogComponent, dialogConfig);
  }

  createReservation() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      entityId: this.entityDetails.id,
      entityType: this.entityType
    }      
    this.dialog.open(ReservationDialogComponent, dialogConfig);
  }

  ngOnDestroy() {
    this.routerSub.unsubscribe();
  }

}
