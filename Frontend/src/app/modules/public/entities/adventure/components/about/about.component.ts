import { Component, Input } from '@angular/core';

@Component({
  selector: 'adventure-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent {

  @Input() aboutSeller!: string;
  @Input() fishingEquipment!: Array<string>;
  @Input() sellerImg!: string;

}
