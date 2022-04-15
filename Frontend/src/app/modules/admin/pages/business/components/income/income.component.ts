import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { GlobalConfig } from 'src/app/models/config/global-config.model';
import { ConfigService } from 'src/app/modules/admin/services/config.service';

@Component({
  selector: 'app-income',
  templateUrl: './income.component.html',
  styleUrls: ['./income.component.scss']
})
export class IncomeComponent implements OnInit {
  globalConfig: GlobalConfig = new GlobalConfig(-1, -1, -1, -1);
  isBtnDisabled: boolean = true;

  constructor(private configService: ConfigService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.configService.getGlobalConfig().subscribe(
      data => this.globalConfig = data
    )
  }

  dataChanged(data: any): void {
    this.isBtnDisabled = false;
  }

  updateGlobalConfig(): void {
    this.configService.getGlobalConfig().subscribe(
      data => {
        let config = data;
        this.globalConfig.buyerPointsPerReservation = config.buyerPointsPerReservation;
        this.globalConfig.sellerPointsPerReservation = config.sellerPointsPerReservation;
        this.configService.updateGlobalConfig(this.globalConfig).subscribe(
          data => {
            this.toastr.success("Income successfully updated.", "Success");
          }, 
          error => {
            this.toastr.error("Error updating", "Error");
          }
        );
      })
    
    this.isBtnDisabled = true;
  }
}
