import { Component, Input, OnInit } from '@angular/core';
import { GlobalConfig } from 'src/app/models/config/global-config.model';
import { LoyaltyConfig, LoyaltyType } from 'src/app/models/config/loyalty-config.model';
import { ConfigService } from 'src/app/modules/admin/services/config.service';

@Component({
  selector: 'app-loyalty-settings',
  templateUrl: './loyalty-settings.component.html',
  styleUrls: ['./loyalty-settings.component.scss']
})
export class LoyaltySettingsComponent implements OnInit {
  globalConfig: GlobalConfig = new GlobalConfig(-1, -1, -1, -1);
  globalConfigChanged: boolean = false;
  loyaltyConfig: LoyaltyConfig[] = [];
  loyaltyConfigChanged: number[] = [];
  isBtnDisabled: boolean = true;

  constructor(private configService: ConfigService) {
    let loyaltyConfig = {id: -1, loyaltyType: LoyaltyType.BABY_SEAHORSE, buyerMinPoints: -1, sellerMinPoints: -1, discount: -1, extraRevenue: -1};
    this.loyaltyConfig.push(loyaltyConfig);
    this.loyaltyConfig.push(loyaltyConfig);
    this.loyaltyConfig.push(loyaltyConfig);
    this.loyaltyConfig.push(loyaltyConfig);
   }

  ngOnInit(): void {
    this.configService.getGlobalConfig().subscribe(
      data => this.globalConfig = data
    );

    this.configService.getLoyaltyConfig().subscribe(
      data => {
        this.loyaltyConfig = data;
        this.loyaltyConfig = this.loyaltyConfig.sort((l1, l2) => {
          if (l1.id > l2.id) {
            return 1;
        }
    
        if (l1.id < l2.id) {
            return -1;
        }
    
        return 0;
        })
      }
    );
  }

  globalConfigDataChanged(data: any): void {
    this.globalConfigChanged = true;
    this.isBtnDisabled = false;
  }

  loyaltyConfigDataChanged(i: number): void {
    this.loyaltyConfigChanged.push(i);
    this.loyaltyConfigChanged = [...new Set(this.loyaltyConfigChanged)]
    this.isBtnDisabled = false;
  }

  updateConfig(): void {
    if(this.globalConfigChanged){
      this.configService.updateGlobalConfig(this.globalConfig).subscribe();
    }
    for(let i of this.loyaltyConfigChanged){
      this.configService.updateLoyaltyConfig(this.loyaltyConfig[i]).subscribe();
    }
    this.loyaltyConfigChanged = [];
    this.isBtnDisabled = true;
  }
}
