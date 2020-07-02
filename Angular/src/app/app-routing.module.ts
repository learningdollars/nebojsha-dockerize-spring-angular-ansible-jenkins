import {AboutComponent} from './about/about.component';
import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {DriverListComponent} from './driver-list/driver-list.component';
import {DriverDetailsComponent} from './driver-details/driver-details.component';
import {TeamListComponent} from './team-list/team-list.component';

const routes: Routes = [
  {path: 'about', component: AboutComponent},
  {path: 'home', component: HomeComponent},
  {path: 'drivers', component: DriverListComponent},
  {path: 'teams', component: TeamListComponent},
  {path: 'driverDetails/:name', component: DriverDetailsComponent},
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
