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
              "rgb(8, 75, 131)"
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
