import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PublicHeaderComponent } from './shared/layout/headers/public-header/public-header.component';
import { SharedModule } from './shared/shared.module';
import { FooterComponent } from './shared/layout/footer/footer.component';


@NgModule({
  declarations: [
    AppComponent,
    PublicHeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
