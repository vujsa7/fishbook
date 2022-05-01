import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/app/modules/public/components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'boats', loadChildren: () => import('./entities/entities.module').then(m => m.EntitiesModule)},
  { path: 'houses', loadChildren: () => import('./entities/entities.module').then(m => m.EntitiesModule)},
  { path: 'adventures', loadChildren: () => import('./entities/entities.module').then(m => m.EntitiesModule)},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
