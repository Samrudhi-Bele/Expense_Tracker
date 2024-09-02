import { RouterModule, Routes } from '@angular/router';
import { ExpenseTrackerComponent } from './expense-tracker/expense-tracker.component';
import { ExpenseServiceService } from './expense-service.service';
import { NgModule } from '@angular/core';

export const routes: Routes = [
  {path:'expense-tracker',component:ExpenseTrackerComponent},
    { path: '', redirectTo: '/expense-tracker', pathMatch: 'full' }, // Default route
    //{ path: '**', redirectTo: '/expense-tracker' } // Wildcard for unmatched routes
];
@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutingModule { }