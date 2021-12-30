import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistrationRequestsComponent } from './pages/registration-requests/registration-requests.component';

const routes: Routes = [
  { path: 'registration-requests', component: RegistrationRequestsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
