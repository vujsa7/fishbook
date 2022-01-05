import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusinessComponent } from './pages/business/business.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { NewBoatComponent } from './pages/new-boat/new-boat.component';
import { RegisterComponent } from './pages/register/register.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { RequestsComponent } from './pages/requests/requests.component';
import { ServicesComponent } from './pages/services/services.component';
import { UsersComponent } from './pages/users/users.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},
  { path: 'business', component: BusinessComponent},
  { path: 'requests', component: RequestsComponent},
  { path: 'reports', component: ReportsComponent},
  { path: 'services', component: ServicesComponent},
  { path: 'users', component: UsersComponent},
  { path: 'new-boat', component: NewBoatComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
