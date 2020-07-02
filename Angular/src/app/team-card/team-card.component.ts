import { Team } from "./../dto/Team";
import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-team-card",
  templateUrl: "./team-card.component.html",
  styleUrls: ["./team-card.component.css"]
})
export class TeamCardComponent implements OnInit {
  @Input() teamObject: Team;
  showMore: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  setShowMore(showMore) {
    this.showMore = showMore;
  }
}
