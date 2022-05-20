import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { SubscriptionService } from './services/subscription.service';
import { StatsService } from './services/stats.service';
import { DashboardComponent } from './dashboard.component';
import { ReservationHistoryComponent } from './components/reservation-history/reservation-history.component';
import { SubscribedEntitiesComponent } from './components/subscribed-entities/subscribed-entities.component';
import { StatsComponent } from './components/stats/stats.component';
import { ReportDialogComponent } from './components/report-dialog/report-dialog.component';
import { RateExperienceDialogComponent } from './components/rate-experience-dialog/rate-experience-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';

@NgModule({
  declarations: [
    DashboardComponent,
    ReservationHistoryComponent,
    SubscribedEntitiesComponent,
    StatsComponent,
    ReportDialogComponent,
    RateExperienceDialogComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    NgChartsModule
  ],
  providers: [
    SubscriptionService,
    StatsService
  ]
})
export class DashboardModule { }
