import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntityStatisticsComponent } from './pages/entity-statistics/entity-statistics.component';
import { MyEntitiesComponent } from './pages/my-entities/my-entities.component';
import { NewEntityComponent } from './pages/new-entity/new-entity.component';
import { StatisticsComponent } from './pages/statistics/statistics.component';
import { SellerComponent } from './seller.component';

const routes: Routes = [
  { path: 'new-boat', component: NewEntityComponent },
  { path: 'new-house', component: NewEntityComponent },
  { path: 'new-adventure', component: NewEntityComponent},
  { path: 'my-boats', component: MyEntitiesComponent },
  { path: 'my-houses', component: MyEntitiesComponent },
  { path: 'my-adventures', component: MyEntitiesComponent },
  { path: 'edit-adventure/:id', component: NewEntityComponent},
  { path: 'edit-boat/:id', component: NewEntityComponent},
  { path: 'edit-house/:id', component: NewEntityComponent},
  { path: 'statistics', component: StatisticsComponent },
  { path: 'statistics/:id', component: EntityStatisticsComponent },
  { path: '', component: SellerComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellerRoutingModule { }
