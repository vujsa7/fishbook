import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { BusinessComponent } from './pages/business/business.component';
import { LoyaltySettingsComponent } from './pages/business/components/loyalty-settings/loyalty-settings.component';
import { AdminFormComponent } from './pages/business/components/admin-form/admin-form.component';
import { IncomeComponent } from './pages/business/components/income/income.component';
import { RegistrationRequestsComponent } from './pages/requests/components/registration-requests/registration-requests.component';
import { ConfirmationDialogComponent } from './pages/requests/components/confirmation-dialog/confirmation-dialog.component';
import { RequestsComponent } from './pages/requests/requests.component';
import { UsersComponent } from './pages/users/users.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { ServicesComponent } from './pages/services/services.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PasswordRenewalComponent } from './pages/password-renewal/password-renewal.component';


@NgModule({
  declarations: [
    AdminComponent,
    BusinessComponent,
    LoyaltySettingsComponent,
    AdminFormComponent,
    IncomeComponent,
    RegistrationRequestsComponent,
    ConfirmationDialogComponent,
    RequestsComponent,
    UsersComponent,
    ReportsComponent,
    ServicesComponent,
    PasswordRenewalComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule
  ],
})
export class AdminModule { }
