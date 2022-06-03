import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { CalendarOptions, FullCalendarComponent } from '@fullcalendar/angular';
import { SellerAvailabilityService } from 'src/app/modules/seller/services/seller-availability.service';
import { SpecialOfferService } from 'src/app/modules/seller/services/special-offer.service';
import { CalendarEvent } from '../../models/calendar-event.model';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

  @Input() sellerId: number = -1;
  @Input() entityType: string = '';
  @Input() entityId: number = -1;
  @ViewChild('calendar') fullCalendar!: FullCalendarComponent;
  sellerAvailabilities!: any;
  specialOffers!: any;
  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    events: (fetchInfo, sucessCallback, failureCallback) => {
      sucessCallback(this.events);
    },
    eventBorderColor : "#ffffff"
  };
  events : CalendarEvent[] = [];
  
  constructor(private sellerAvailabilityService: SellerAvailabilityService, private specialOfferService: SpecialOfferService) { }

  ngOnInit(): void {
    if(this.entityType == "adventure") {
      this.displaySellerAvailabilities();
    }
    this.displaySpecialOffers();
  }

  private displaySellerAvailabilities() {
    this.sellerAvailabilityService.getAvailabilities(this.sellerId).subscribe(
      data => { 
        this.sellerAvailabilities = data;
        for(let sellerAvailability of this.sellerAvailabilities){
          this.events.push(
            new CalendarEvent(
              sellerAvailability.id,
              "Available ",
              sellerAvailability.fromDateTime,
              sellerAvailability.toDateTime,
              "rgb(16, 206, 184)"
            )
          );
        }
        if(this.fullCalendar){
          this.fullCalendar.getApi().refetchEvents();
        }
      }
    )
  }

  private displaySpecialOffers() {
    this.specialOfferService.getSpecialOffers(this.entityId).subscribe(
      data => { 
        this.specialOffers = data;
        for(let specialOffer of this.specialOffers){
          this.events.push(
            new CalendarEvent(
              specialOffer.id,
              "Special offer ",
              specialOffer.startDateTime,
              specialOffer.endDateTime,
              "linear-gradient(265.59deg, #4BB3FD -1.67%, #6F10CE 99.98%, rgba(110, 17, 205, 0.0104167) 99.99%, rgba(111, 16, 206, 0) 100%)"
            )
          );
        }
        if(this.fullCalendar){
          this.fullCalendar.getApi().refetchEvents();
        }
      }
    )
  }

}
