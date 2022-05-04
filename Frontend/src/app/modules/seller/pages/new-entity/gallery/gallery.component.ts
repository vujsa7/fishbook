import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BoatService } from 'src/app/shared/services/boat.service';
import { ImageService } from 'src/app/shared/services/image.service';
import { EntityImage } from '../../../../../shared/models/entity-image.model';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { ImageFile } from '../../../models/image-file';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {
  @Input() entityRegistrationRequest!: any;
  @Input() entityType!: string;
  imageFiles: Array<ImageFile> = new Array();
  mainPhotoUploaded: boolean = false;
  coverPhotoUploaded: boolean = false;
  mainPhotoUrl: any;
  coverPhotoUrl: any;
  smallPhotoUrls: Array<any> = new Array(7);

  constructor(private boatService: BoatService, private imageService: ImageService, private route: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  onFileChanged(event: any, priority: number, isNewImage: boolean): void {
    const file = event.target.files[0]
    const mimeType = file.type;
    if (mimeType.match(/image\/*/) == null) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        title: "Only images are supported",
        message: "Please select an image to upload",
        buttonText: "Okay"
      };
      const dialogRef = this.dialog.open(InfoDialogComponent, dialogConfig);
      return;
    }

    const reader = new FileReader();
    if (priority == 0) {
      reader.readAsDataURL(file); 
      reader.onload = (_event) => { 
          this.mainPhotoUrl = reader.result; 
      }
      this.mainPhotoUploaded = true;
    } else if (priority == 1) {
      reader.readAsDataURL(file); 
      reader.onload = (_event) => { 
          this.coverPhotoUrl = reader.result; 
      }
      this.coverPhotoUploaded = true;
    } else {
      reader.readAsDataURL(file); 
      reader.onload = (_event) => {
          this.smallPhotoUrls[priority - 2] = reader.result;
      }
    }

    if(isNewImage) {
      const index = this.imageFiles.findIndex(x => x.priority == priority);
      this.imageFiles.splice(index, 1, new ImageFile(file, priority));
    } else {
      this.imageFiles.push(new ImageFile(file, priority));
    }

  }

  onSubmit(){
    if(this.mainPhotoUploaded && this.coverPhotoUploaded) {
      if(this.entityType == "boat") {
        this.boatService.postRegistrationRequest(this.entityRegistrationRequest).subscribe(
          data => {
            this.postImages(data);
          }
        )
      }
    } else {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        title: "Photos required",
        message: "Before publishing, please make sure to provide a main and cover photo for your boat",
        buttonText: "Okay"
      };
      this.dialog.open(InfoDialogComponent, dialogConfig);
    }
  }

  private postImages(boatId: any){
    for(let img of this.imageFiles){
      let entityImage = new EntityImage(img.priority, boatId);
      const image = new FormData();
      image.append('file', img.content);
      image.append('image', new Blob([JSON.stringify(entityImage)], { type: 'application/json' }));
      this.imageService.postImage(image).subscribe();
    }
    this.route.navigate(["/homepage"]);
  }

}
