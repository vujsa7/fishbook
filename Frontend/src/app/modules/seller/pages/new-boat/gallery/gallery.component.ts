import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BoatService } from 'src/app/shared/services/boat.service';
import { ImageService } from 'src/app/shared/services/image.service';
import { BoatRegistrationRequest } from '../../../models/boat-registration-request';
import { EntityImage } from '../../../models/entity-image.model';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {
  @Input() boatRegistrationRequest!: BoatRegistrationRequest;
  imageFiles: Array<any> = new Array();

  constructor(private boatService: BoatService, private imageService: ImageService, private route: Router) { }

  ngOnInit(): void {
  }

  onFileChanged(event: any, priority: number): void {
    const file = event.target.files[0]
    this.imageFiles.push({
      content: file,
      pri: priority
    });
  }

  onSubmit(){
    // this.postImages();
    this.boatService.postRegistrationRequest(this.boatRegistrationRequest).subscribe(
      data => {
        this.postImages(data);
      }
    )
  }

  private postImages(boatId: any){
    for(let img of this.imageFiles){
      let entityImage = new EntityImage('', img.pri, boatId);
      const image = new FormData();
      image.append('file', img.content);
      image.append('image', new Blob([JSON.stringify(entityImage)], { type: 'application/json' }));
      this.imageService.postImage(image).subscribe();
    }
    this.route.navigate(["/homepage"]);
  }

}
