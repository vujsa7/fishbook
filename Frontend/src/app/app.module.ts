import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from './shared/shared.module';
import { PublicHeaderComponent } from './shared/layout/headers/public-header/public-header.component';
import { ClientHeaderComponent } from './shared/layout/headers/client-header/client-header.component';
import { FooterComponent } from './shared/layout/footer/footer.component';
import { AdminHeaderComponent } from './shared/layout/headers/admin-header/admin-header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    PublicHeaderComponent,
    ClientHeaderComponent,
    FooterComponent,
    AdminHeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    BrowserAnimationsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
