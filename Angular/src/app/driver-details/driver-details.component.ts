import {Component, Input, OnInit} from '@angular/core';
import {Driver} from '../dto/Driver';
import {Subject} from 'rxjs';
import {ApiService} from '../api.service';
import {ActivatedRoute, Router} from '@angular/router';
import {filter, switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-driver-details',
  templateUrl: './driver-details.component.html',
  styleUrls: ['./driver-details.component.css']
})
export class DriverDetailsComponent implements OnInit {

  driverObject: Driver;
  language$ = new Subject();
  name$ = new Subject();

  constructor(private api: ApiService, private route: ActivatedRoute, private router: Router) {
  }


  ngOnInit(): void {
    this.language$.pipe(
      switchMap(it => this.api
        .getDriverDetails(this.route.snapshot.paramMap.get('name'), `${it}`)))
      .subscribe(it => {
        this.changeDriverObject(it);
      });

    this.name$.pipe(
      switchMap(it => {
          if (this.route.snapshot.queryParamMap.get('language')) {
            return this.api
              .getDriverDetails(`${it}`, this.route.snapshot.queryParamMap.get('language'));
          } else {
            return this.api
              .getDriverDetails(`${it}`, 'en');
          }
        }
      )).subscribe(result => {
      this.changeDriverObject(result);
    });

    this.route.queryParamMap.subscribe(it => {
      if (!it || !it.get('language')) {
        this.router.navigate([], {
          queryParams: {language: 'en'},
          relativeTo: this.route,
          queryParamsHandling: 'merge'
        });
      } else {
        this.language$.next(it.get('language'));
      }
    });

    this.route.paramMap.pipe(
      filter(it => {
        if (it.get('name')) {
          return true;
        }
        return false;
      })
    ).subscribe(it => this.name$.next(it.get('name')));

  }

  changeDriverObject(apiResult) {
    const basicInfo = apiResult.basic[0];
    const extendedInfo = apiResult.extended[0];
    this.driverObject = Object.assign(basicInfo, extendedInfo);
  }

}
