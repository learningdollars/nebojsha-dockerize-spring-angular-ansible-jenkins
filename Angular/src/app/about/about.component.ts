import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  abstract: string;
  f1URI: string;
  language$ = new Subject();

  constructor(private api: ApiService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {

    this.language$.pipe(
      switchMap(it => this.api.getFOneAbstract(`${it}`)))
      .subscribe(it => {
        this.abstract = it[0].abstract.value;
        this.f1URI = it[0].subject.value;
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
  }
}
