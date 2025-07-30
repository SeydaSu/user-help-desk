import { Routes } from '@angular/router';
import { HomeComponent } from './home/home';
import { LoginComponent } from './auth/login/login';
import { RegisterComponent } from './auth/register/register';
import { Dashboard } from './dashboard/dashboard';
import { TicketCreateComponent } from './tickets/components/ticket-create/ticket-create';
import { TicketListComponent } from './tickets/components/ticket-list/ticket-list';


export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: Dashboard }, 
  { path: 'ticket', component: TicketCreateComponent},
  { path: 'tickets', component: TicketListComponent},

  { path: '**', redirectTo: '' }
];



