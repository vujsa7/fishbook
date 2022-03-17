import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewBoatComponent } from './pages/new-boat/new-boat.component';
import { SellerComponent } from './seller.component';

const routes: Routes = [
  { path: '', component: SellerComponent },
  { path: 'new-boat', component: NewBoatComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellerRoutingModule { }
