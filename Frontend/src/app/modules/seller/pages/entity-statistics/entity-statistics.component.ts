import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-entity-statistics',
  templateUrl: './entity-statistics.component.html',
  styleUrls: ['./entity-statistics.component.scss']
})
export class EntityStatisticsComponent implements OnInit {

  currentDate: Date = new Date();
  reservations!: any;

  chartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    maintainAspectRatio: false,
  };
  chartLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'];
  chartType: ChartType = "bar";
  chartLegend = false;
  chartData = [
    {data: Array<any>(), label: 'Reservations', cubicInterpolationMode: 'monotone', backgroundColor: '#03A1FC'},
    {data: Array<any>(), label: 'Cancelled reservations', cubicInterpolationMode: 'monotone', backgroundColor: '#FC2003'}
  ];

  @ViewChild(BaseChartDirective) chart!: BaseChartDirective;

  constructor(private route: ActivatedRoute, private reservationService: ReservationService) { }

  ngOnInit(): void {
    let entityId = this.route.snapshot.params['id'];
    this.reservationService.getReservations(entityId).subscribe(
      data => { 
        this.reservations = data;
        this.filterReservationsForGraph();
        this.chart.update();
      }
    );
  }

  filterReservationsForGraph() {
    for(let month = 0; month < 12; month++){
      var firstDateInMonth = new Date(new Date().getFullYear(), month, 1);
      var lastDateInMonth = new Date(new Date().getFullYear(), month+1, 0);
      var reservationsCount = 0;
      var cancelledReservationsCount = 0;
      for(let reservation of this.reservations){
        if(new Date(reservation.startDateTime) > firstDateInMonth && new Date(reservation.endDateTime) < lastDateInMonth){
          if(reservation.isCancelled) {
            cancelledReservationsCount += 1;
          } else {
            reservationsCount += 1;
          }
        }
      }
      this.chartData[0].data.push(reservationsCount);
      this.chartData[1].data.push(cancelledReservationsCount);
    }
  }

}
