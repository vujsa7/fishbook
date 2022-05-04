import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewEntityComponent } from './pages/new-entity/new-entity.component';
import { SellerComponent } from './seller.component';

const routes: Routes = [
  { path: 'new-boat', component: NewEntityComponent },
  { path: 'new-house', component: NewEntityComponent },
  { path: 'new-adventure', component: NewEntityComponent},
  { path: '', component: SellerComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellerRoutingModule { }
