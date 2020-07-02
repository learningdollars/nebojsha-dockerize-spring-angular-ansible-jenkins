import { Component, OnInit } from "@angular/core";
import { Subject } from "rxjs";
import { ApiService } from "../api.service";
import { ActivatedRoute, Router } from "@angular/router";
import { switchMap } from "rxjs/operators";
import { Driver } from "../dto/Driver";

@Component({
  selector: "app-driver-list",
  templateUrl: "./driver-list.component.html",
  styleUrls: ["./driver-list.component.css"]
})
export class DriverListComponent implements OnInit {
  language$ = new Subject();
  drivers: Array<Driver>;
  searchTerm$ = new Subject();
  limit = 9;
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
            return this.api.searchDrivers(
              this.route.snapshot.queryParamMap.get("searchTerm"),
              `${it}`,
              this.limit
            );
          } else {
            return this.api.getListOfDrivers( `${it}`, this.limit);
          }
        })
      )
      .subscribe(it => {
        this.drivers = it;
      });

    this.searchTerm$
      .pipe(
        switchMap(it =>
          this.api.searchDrivers(
            `${it}`,
            this.route.snapshot.queryParamMap.get("language"),
            this.limit
          )
        )
      )
      .subscribe(it => {
        console.log(it);
        this.drivers = it;
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
