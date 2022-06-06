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
import { DeleteRequestsComponent } from './pages/requests/components/delete-requests/delete-requests.component';
import { DeleteRequestResponseDialogComponent } from './pages/requests/components/delete-request-response-dialog/delete-request-response-dialog.component';
import { SellerReportResponseDialogComponent } from './pages/reports/components/seller-report-response-dialog/seller-report-response-dialog.component';
import { BuyerReportsComponent } from './pages/reports/components/buyer-reports/buyer-reports.component';
import { BuyerReportResponseDialogComponent } from './pages/reports/components/buyer-report-response-dialog/buyer-report-response-dialog.component';


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
    PasswordRenewalComponent,
    DeleteRequestsComponent,
    DeleteRequestResponseDialogComponent,
    SellerReportResponseDialogComponent,
    BuyerReportsComponent,
    BuyerReportResponseDialogComponent
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
