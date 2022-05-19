import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ReservationHistoryComponent } from './components/reservation-history/reservation-history.component';
import { SubscribedEntitiesComponent } from './components/subscribed-entities/subscribed-entities.component';
import { StatsComponent } from './components/stats/stats.component';
import { ReservationHistoryService } from './services/reservation-history.service';
import { NgChartsModule } from 'ng2-charts';
import { SubscriptionService } from './services/subscription.service';
import { StatsService } from './services/stats.service';

@NgModule({
  declarations: [
    DashboardComponent,
    ReservationHistoryComponent,
    SubscribedEntitiesComponent,
    StatsComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    NgChartsModule
  ],
  providers: [
    ReservationHistoryService,
    SubscriptionService,
    StatsService
  ]
})
export class ClientModule { }
