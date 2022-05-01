import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntityDetailsComponent } from './components/entity-details/entity-details.component';
import { EntitiesComponent } from './entities.component';

const routes: Routes = [
  { path: ':id', component: EntityDetailsComponent},
  { path: '', component: EntitiesComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EntitiesRoutingModule { }
