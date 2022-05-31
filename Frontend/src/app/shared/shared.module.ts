import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { UserTypePipe } from './pipes/user-type.pipe';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { MessageDialogComponent } from './components/message-dialog/message-dialog.component';
import { GlassContainerComponent } from './components/glass-container/glass-container.component';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import { DatePickerComponent } from './components/date-picker/date-picker.component';
import { MaterialModule } from './material/material.module';
import { EntityCardComponent } from './components/entity-card/entity-card.component';
import { SpinnerFullscreenComponent } from './layout/spinner-fullscreen/spinner-fullscreen.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RangeDatePickerComponent } from './components/range-date-picker/range-date-picker.component';
import { LoyaltyPointsComponent } from './components/loyalty-points/loyalty-points.component';
import { BaseHeaderComponent } from './layout/headers/base-header/base-header.component';
import { ClientLinksComponent } from './layout/headers/client-links/client-links.component';
import { AdminLinksComponent } from './layout/headers/admin-links/admin-links.component';
import { PublicLinksComponent } from './layout/headers/public-links/public-links.component';
import { SellerLinksComponent } from './layout/headers/seller-links/seller-links.component';
import { DeleteAccountDialogComponent } from './components/delete-account-dialog/delete-account-dialog.component';
import { LoyaltyTypePipe } from './pipes/loyalty-type.pipe';
import { OptionsDialogComponent } from './components/options-dialog/options-dialog.component';
import { MapComponent } from './components/map/map.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

FullCalendarModule.registerPlugins([
  dayGridPlugin,
  interactionPlugin
]);

@NgModule({
  declarations: [
    PrimaryButtonComponent,
    UserTypePipe,
    LoyaltyTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    InfoDialogComponent,
    DatePickerComponent,
    EntityCardComponent,
    SpinnerFullscreenComponent,
    ProfileComponent,
    RangeDatePickerComponent,
    LoyaltyPointsComponent,
    BaseHeaderComponent,
    ClientLinksComponent,
    AdminLinksComponent,
    PublicLinksComponent,
    SellerLinksComponent,
    DeleteAccountDialogComponent,
    OptionsDialogComponent,
    MapComponent,
    CalendarComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    MaterialModule,
    FullCalendarModule
  ],
  exports: [
    PrimaryButtonComponent,
    UserTypePipe,
    LoyaltyTypePipe,
    SecondaryButtonComponent,
    MessageDialogComponent,
    GlassContainerComponent,
    InfoDialogComponent,
    DatePickerComponent,
    EntityCardComponent,
    SpinnerFullscreenComponent,
    MaterialModule,
    RangeDatePickerComponent,
    BaseHeaderComponent,
    ClientLinksComponent,
    AdminLinksComponent,
    PublicLinksComponent,
    OptionsDialogComponent,
    MapComponent,
    CalendarComponent
  ]
})
export class SharedModule { }
