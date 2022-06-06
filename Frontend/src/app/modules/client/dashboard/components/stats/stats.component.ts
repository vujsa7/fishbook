import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Chart, ChartItem } from 'chart.js';
import { StatsInfo } from '../../models/stats-info.model';
import { StatsService } from '../../services/stats.service';

@Component({
  selector: 'db-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.scss']
})
export class StatsComponent implements OnInit {

  public statsInfo!: StatsInfo;
  public pointsChart!: Chart;
  public penaltiesChart!: Chart;

  constructor(private statsService: StatsService) { }

  ngOnInit(): void {

    let ctx = document.getElementById('pointsChart') as ChartItem;
    this.pointsChart = new Chart(ctx, {
      type: 'doughnut',
      data: this.pointsData,
      options: this.options
    });

    ctx = document.getElementById('penaltiesChart') as ChartItem;
    this.penaltiesChart = new Chart(ctx, {
      type: 'doughnut',
      data: this.penaltiesData,
      options: this.options
    });

    this.fetchStatsInfoForChart();
  }

  fetchStatsInfoForChart() {
      this.statsService.fetchStatsInfo().subscribe(data => {
        this.statsInfo = data;
        this.pointsChart.data.datasets[0].data[0] = this.statsInfo.currentPoints;
        this.pointsChart.data.datasets[0].data[1] = this.statsInfo.pointsForNextLevel - this.statsInfo.currentPoints;
        this.pointsChart.update();
        this.penaltiesChart.data.datasets[0].data[0] = this.statsInfo.currentPenalties;
        this.penaltiesChart.data.datasets[0].data[1] = this.statsInfo.penaltiesForBan  - this.statsInfo.currentPenalties;
        this.penaltiesChart.update();
      });
  }

  private pointsData = {
    labels: ['Current Points', 'Points For Next Level'],
    datasets: [
      {
        data: [0, 0],
        backgroundColor: [
          'rgb(75, 179, 253)',
          'rgb(242, 242, 242)'
        ],
        hoverBackgroundColor: [
          'rgb(56, 166, 243)',
          'rgb(225, 225, 225)'
        ],
        borderWidth: 0
      }
    ],
  };

  private penaltiesData = {
    labels: ['Penalties', 'Penalties For Ban'],
    datasets: [
      {
        data: [0, 0],
        backgroundColor: [
          'rgb(228, 50, 50)',
          'rgb(242, 242, 242)'
        ],
        hoverBackgroundColor: [
          'rgb(211, 33, 33)',
          'rgb(225, 225, 225)'
        ],
        borderWidth: 0
      }
    ],
  };

  private options = {
    cutout: '80%',
    plugins: {
      legend: {
        display: false
      }
    }
  }

}
