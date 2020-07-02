import { Team } from "./../dto/Team";
import { Component, OnInit } from "@angular/core";
import { Subject } from "rxjs";
import { ApiService } from "../api.service";
import { ActivatedRoute, Router } from "@angular/router";
import { switchMap } from "rxjs/operators";

@Component({
  selector: "app-team-list",
  templateUrl: "./team-list.component.html",
  styleUrls: ["./team-list.component.css"]
})
export class TeamListComponent implements OnInit {
  language$ = new Subject();
  teams: Array<Team>;
  searchTerm$ = new Subject();
  limit = 150;
  searchText: string | null;

  constructor(
    private api: ApiService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.language$
      .pipe(
        switchMap(it => {
          if (this.route.snapshot.queryParamMap.get("searchTerm")) {
            return this.api.searchTeams(
              this.route.snapshot.queryParamMap.get("searchTerm"),
              `${it}`,
              this.limit
            );
          } else {
            return this.api.getListOfTeams(`${it}`, this.limit);
          }
        })
      )
      .subscribe(it => {
        this.teams = it;
      });

    this.searchTerm$
      .pipe(
        switchMap(it =>
          this.api.searchTeams(
            `${it}`,
            this.route.snapshot.queryParamMap.get("language"),
            this.limit
          )
        )
      )
      .subscribe(it => {
        this.teams = it;
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
      if (it && it.get("searchTerm")) {
        this.searchText = it.get("searchTerm");
        this.searchTerm$.next(it.get("searchTerm"));
      }
    });
  }

  search() {
    if (this.searchText && this.searchText !== "") {
      this.router.navigate([], {
        queryParams: { searchTerm: this.searchText },
        relativeTo: this.route,
        queryParamsHandling: "merge"
      });
    } else {
      this.router.navigate([], {
        queryParams: {
          language: this.route.snapshot.queryParamMap.get("language")
        },
        relativeTo: this.route
      });
    }
  }

  ngOnDestroy(): void {
    this.router.navigate([], {
      queryParams: {
        language: this.route.snapshot.queryParamMap.get("language")
      },
      relativeTo: this.route
    });
  }
}
