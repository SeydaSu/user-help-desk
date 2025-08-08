import { Routes } from '@angular/router';
import { HomeComponent } from './home/home';
import { LoginComponent } from './auth/login/login';
import { RegisterComponent } from './auth/register/register';
import { Dashboard } from './dashboard/dashboard';
import { TicketCreateComponent } from './tickets/components/ticket-create/ticket-create';
import { TicketListComponent } from './tickets/components/ticket-list/ticket-list';
import { TicketDetailComponent } from './tickets/components/ticket-detail/ticket-detail';
import { TicketUpdateComponent } from './tickets/components/ticket-update/ticket-update'
import { LoginAdminComponent } from './auth/login-admin/login-admin';
import { AdminDashboard } from './admin/components/admin-dashboard/admin-dashboard';
import { AddTags } from './admin/components/add-tags/add-tags';
import { AddPriorities } from './admin/components/add-priorities/add-priorities';
import { AddStatuses } from './admin/components/add-statuses/add-statuses';
import { AdminTicketListComponent } from './admin/components/admin-ticket-list/admin-ticket-list';


export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: Dashboard }, 
  { path: 'admin-dashboard', component: AdminDashboard},
  { path: 'ticket', component: TicketCreateComponent},
  { path: 'tickets', component: TicketListComponent},
  { path: 'ticket/:id', component: TicketDetailComponent },
  { path: 'admin_login', component: LoginAdminComponent},
  { path: 'add-tags', component: AddTags},
  { path: 'add-priorities', component: AddPriorities},
  { path: 'add-statuses', component: AddStatuses},
  { path: 'admin-ticket-list', component: AdminTicketListComponent},
  { path: 'ticket/update/:id', component: TicketUpdateComponent},

  { path: '**', redirectTo: '' }
];



