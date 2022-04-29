import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewBoatComponent } from './pages/new-boat/new-boat.component';
import { SellerComponent } from './seller.component';

const routes: Routes = [
  { path: 'new-boat', component: NewBoatComponent },
  { path: '', component: SellerComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SellerRoutingModule { }
