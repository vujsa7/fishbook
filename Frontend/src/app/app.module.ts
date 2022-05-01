import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from './shared/shared.module';
import { PublicHeaderComponent } from './shared/layout/headers/public-header/public-header.component';
import { ClientHeaderComponent } from './shared/layout/headers/client-header/client-header.component';
import { SellerHeaderComponent } from './shared/layout/headers/seller-header/seller-header.component';
import { FooterComponent } from './shared/layout/footer/footer.component';
import { AdminHeaderComponent } from './shared/layout/headers/admin-header/admin-header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

FullCalendarModule.registerPlugins([ // register FullCalendar plugins
  dayGridPlugin,
  interactionPlugin
]);

@NgModule({
  declarations: [
    AppComponent,
    PublicHeaderComponent,
    ClientHeaderComponent,
    FooterComponent,
    AdminHeaderComponent,
    SellerHeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FullCalendarModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
