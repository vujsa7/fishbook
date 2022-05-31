import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/authentication/auth.service';

@Component({
  selector: 'app-loyalty-points',
  templateUrl: './loyalty-points.component.html',
  styleUrls: ['./loyalty-points.component.scss']
})
export class LoyaltyPointsComponent implements OnInit {

  constructor(private authService: AuthService){}

  @Input() points: number = -1;
  @Input() levelMarks!: Array<number>;
  profileType: string = "";
  lowPointsIcon: string = "";
  previousLevelTarget: number = -1;
  highPointsIcon: string = "";
  nextLevelTarget: number = -1;

  customerLevel: string = "";
  
  ngOnInit(): void {
    this.profileType = this.authService.getTokenRole();
    if (this.profileType == "ROLE_CLIENT"){
      this.initializeCustomerLoyaltyConfig();
    }
  }

  initializeCustomerLoyaltyConfig() {
    if(this.points < this.levelMarks[1]){
      this.lowPointsIcon = "seahorse-icon";
      this.previousLevelTarget = 0;
      this.highPointsIcon = "swordfish-icon";
      this.nextLevelTarget = this.levelMarks[1];
      this.customerLevel = "Baby Seahorse Customer";
    } else if(this.points < this.levelMarks[2]){
      this.lowPointsIcon = "swordfish-icon";
      this.previousLevelTarget = this.levelMarks[1];
      this.highPointsIcon = "shark-icon";
      this.nextLevelTarget = this.levelMarks[2];
      this.customerLevel = "Wild Swordfish Customer";
    } else if(this.points < this.levelMarks[3]){
      this.previousLevelTarget = this.levelMarks[2];
      this.lowPointsIcon = "shark-icon";
      this.highPointsIcon = "whale-icon";
      this.nextLevelTarget = this.levelMarks[3];
      this.customerLevel = "Experienced Shark Customer";
    } else {
      this.previousLevelTarget = this.levelMarks[3];
      this.lowPointsIcon = "whale-icon";
      this.nextLevelTarget = this.levelMarks[3];
      this.customerLevel = "Mighty Whale Customer";
    }
  }

  getRightOffset(): number{
    let offset = 100-(this.points-this.previousLevelTarget)*100/(this.nextLevelTarget-this.previousLevelTarget);
    if(offset < 10) // 10% is 30px and slider is 300px wide, this way we prevent overflow of points on the right from relative div 
      return 10;
    else if(offset > 90)
      return 90; // this way we prevent overflow of points on the left from relative div
    else 
      return offset;
  }


}
