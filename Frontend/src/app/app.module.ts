import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { RegistrationRequestsComponent } from './pages/registration-requests/registration-requests.component';
import { GlassContainerComponent } from './shared/components/glass-container/glass-container.component';
import { PrimaryButtonComponent } from './shared/components/primary-button/primary-button.component';
import { RegistrationTypePipe } from './shared/pipes/registration-type.pipe';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationRequestsComponent,
    PrimaryButtonComponent,
    RegistrationTypePipe,
    HomeComponent,
    RegisterComponent,
    GlassContainerComponent
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
