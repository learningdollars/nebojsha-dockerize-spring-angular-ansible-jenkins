import { Component, OnInit } from "@angular/core";
import { ApiService } from "../api.service";
import { ActivatedRoute, Router } from "@angular/router";
import { switchMap } from "rxjs/operators";
import { Subject } from "rxjs";
import { Driver } from "../dto/Driver";
import { Team } from "../dto/Team";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"]
})
export class HomeComponent implements OnInit {
  drivers: Array<Driver> | null;
  teams: Array<Team> | null;
  language$ = new Subject();

  constructor(
    private api: ApiService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.router.navigate([], {
      queryParams: {
        language: this.route.snapshot.queryParamMap.get("language")
      },
      relativeTo: this.route
    });
    this.language$
      .pipe(switchMap(it => this.api.getListOfDrivers(`${it}`, 3)))
      .subscribe(result => {
        this.drivers = result;
      });

    this.language$
      .pipe(switchMap(it => this.api.getListOfTeams(`${it}`, 3)))
      .subscribe(result => {
        this.teams = result;
      });

    this.route.queryParamMap.subscribe(it => {
      if (!it || !it.get("language")) {
        this.router.navigate([], {
          queryParams: { language: "en" },
          relativeTo: this.route,
          queryParamsHandling: "merge"
        });
      } else {
        this.language$.next(it.get("language"));
      }
    });
  }
}
