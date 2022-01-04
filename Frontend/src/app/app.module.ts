import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { RegistrationRequestsComponent } from './pages/requests/components/registration-requests/registration-requests.component';
import { GlassContainerComponent } from './shared/components/glass-container/glass-container.component';
import { PrimaryButtonComponent } from './shared/components/primary-button/primary-button.component';
import { UserTypePipe } from './shared/pipes/user-type.pipe';
import { ConfirmationDialogComponent } from './pages/requests/components/confirmation-dialog/confirmation-dialog.component';
import { FormsModule } from '@angular/forms';
import { SecondaryButtonComponent } from './shared/components/secondary-button/secondary-button.component';
import { RequestsComponent } from './pages/requests/requests.component';
import { BusinessComponent } from './pages/business/business.component';
import { LoyaltySettingsComponent } from './pages/business/components/loyalty-settings/loyalty-settings.component';
import { AdminFormComponent } from './pages/business/components/admin-form/admin-form.component';
import { IncomeComponent } from './pages/business/components/income/income.component';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationRequestsComponent,
    PrimaryButtonComponent,
    UserTypePipe,
    HomeComponent,
    RegisterComponent,
    GlassContainerComponent,
    ConfirmationDialogComponent,
    SecondaryButtonComponent,
    RequestsComponent,
    BusinessComponent,
    LoyaltySettingsComponent,
    AdminFormComponent,
    IncomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
