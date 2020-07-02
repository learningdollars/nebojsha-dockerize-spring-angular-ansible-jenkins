import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ApiService } from './api.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AboutComponent } from './about/about.component';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { DriverCardComponent } from './driver-card/driver-card.component';
import { DriverListComponent } from './driver-list/driver-list.component';
import {FormsModule} from '@angular/forms';
import { DriverDetailsComponent } from './driver-details/driver-details.component';
import { TeamListComponent } from './team-list/team-list.component';
import { TeamCardComponent } from './team-card/team-card.component';

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    NavbarComponent,
    HomeComponent,
    DriverCardComponent,
    DriverListComponent,
    DriverDetailsComponent,
    TeamListComponent,
    TeamCardComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
