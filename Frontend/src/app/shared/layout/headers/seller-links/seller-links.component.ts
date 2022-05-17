import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-seller-links',
  templateUrl: './seller-links.component.html',
  styleUrls: ['./seller-links.component.scss']
})
export class SellerLinksComponent {

  @Input() role!: string;
}
