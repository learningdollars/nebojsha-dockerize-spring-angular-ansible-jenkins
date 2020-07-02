import {Component, Input, OnInit} from '@angular/core';
import {Driver} from '../dto/Driver';

@Component({
  selector: 'app-driver-card',
  templateUrl: './driver-card.component.html',
  styleUrls: ['./driver-card.component.css']
})
export class DriverCardComponent implements OnInit {

  @Input() driverObject: Driver;
  showMore: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  setShowMore(showMore) {
    this.showMore = showMore;
  }

}
