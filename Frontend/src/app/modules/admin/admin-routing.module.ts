import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusinessComponent } from './pages/business/business.component';
import { PasswordRenewalComponent } from './pages/password-renewal/password-renewal.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { RequestsComponent } from './pages/requests/requests.component';
import { ServicesComponent } from './pages/services/services.component';
import { UsersComponent } from './pages/users/users.component';

const routes: Routes = [
  { path: '', component: BusinessComponent},
  { path: 'business', component: BusinessComponent},
  { path: 'requests', component: RequestsComponent},
  { path: 'reports', component: ReportsComponent},
  { path: 'services', component: ServicesComponent},
  { path: 'users', component: UsersComponent},
  { path: 'password-renewal', component: PasswordRenewalComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
