import { Component, Input } from '@angular/core';

@Component({
  selector: 'adventure-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent {

  @Input() previousAdventureImages!: Array<string>;

}
