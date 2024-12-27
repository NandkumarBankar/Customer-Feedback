import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticatePageComponent } from './components/authenticate-page/authenticate-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DialogComponent } from './components/feedback/dialog.component';

const routes: Routes = [
  { path: '', redirectTo: 'authenticate', pathMatch: 'full' },
  { path: 'authenticate', component: AuthenticatePageComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'feedback', component: DialogComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
