import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationRequestsComponent } from './pages/registration-requests/registration-requests.component';
import { PrimaryButtonComponent } from './shared/components/primary-button/primary-button.component';
import { RegistrationTypePipe } from './shared/pipes/registration-type.pipe';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationRequestsComponent,
    PrimaryButtonComponent,
    RegistrationTypePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
