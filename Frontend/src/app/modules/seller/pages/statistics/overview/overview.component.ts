import { AfterViewInit, Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { EntityStatistic } from '../../../models/entity-statistic.model';
import { EntityService } from '../../../services/entity.service';

@Component({
  selector: 'entities-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {

  displayedColumns: string[] = ['name', 'rating', 'reservations', 'options'];
  dataSource!: MatTableDataSource<EntityStatistic>;
  selectedOptionBoxId: number = -1;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChildren('optionBox') optionBox!: QueryList<ElementRef>;
  @ViewChildren('optionBtn') optionBtn!: QueryList<ElementRef>;

  constructor(private entityService: EntityService, private renderer: Renderer2, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.entityService.getEntityStatics().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.renderer.listen('body', 'click', (e: Event) => {
        for (let el of this.optionBox) {
          if (el.nativeElement.contains(e.target)) {
            return;
          }
        }
        for (let el of this.optionBtn) {
          if (el.nativeElement.contains(e.target)) {
            return;
          }
        }
        this.selectedOptionBoxId = -1;
      })
    })
  }

  selectOptionBox(id: number): void {
    if (id == this.selectedOptionBoxId)
      this.selectedOptionBoxId = -1;
    else
      this.selectedOptionBoxId = id;
  }

  isOptionsBoxVisible(id: number): boolean {
    if (id == this.selectedOptionBoxId)
      return true;
    else
      return false;
  }

}
