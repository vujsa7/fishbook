import { Component, Input } from '@angular/core';

@Component({
  selector: 'entity-images-viewer',
  templateUrl: './images-viewer.component.html',
  styleUrls: ['./images-viewer.component.scss']
})
export class ImagesViewerComponent{

  @Input() images!: Array<string>;

}
