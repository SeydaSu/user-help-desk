import { Component } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../../auth/auth.service';
import { PriorityResponse } from '../../../models/priority.model';
import { StatusResponse } from '../../../models/status.model';
import { TagResponse } from '../../../models/tag.model';
import {
  Ticket,
  TicketAdminUpdateRequest,
  TicketRequest,
} from '../../../models/ticket.model';
import { TicketFacade } from '../../../tickets/store/ticket.facade';
import { CommonModule } from '@angular/common';
import { AdminFacade } from '../../store/admin.facade';

@Component({
  selector: 'app-admin-respond-ticket',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './admin-respond-ticket.html',
  styleUrl: './admin-respond-ticket.css',
})
export class AdminRespondTicketComponent {
  isLoading$: Observable<boolean>;
  ticket$: Observable<Ticket | undefined>;
  error$: Observable<string | undefined>;
  ticketForm!: FormGroup;

  priorityOptions: PriorityResponse[] = [];
  statusOptions: StatusResponse[] = [];
  tagOptions: TagResponse[] = [];
  ticketId!: number;

  showSuccessMessage = false;

  constructor(
    private fb: FormBuilder,
    private ticketFacade: TicketFacade,
    private router: Router,
    private adminFacade: AdminFacade
  ) {
    this.isLoading$ = this.ticketFacade.isLoading$;
    this.error$ = this.ticketFacade.error$;
    this.ticket$ = this.ticketFacade.activeTicket$;
  }

  ngOnInit(): void {
    this.ticket$.subscribe((ticket) => {
      if (ticket) {
        this.ticketId = ticket.id;

        this.ticketForm = this.fb.group({
          title: [ticket.title, Validators.required],
          description: [
            ticket.description,
            [Validators.required, Validators.minLength(10)],
          ],
          response: [ticket.response, Validators.required],
          tagId: [ticket.tagId, Validators.required],
          priorityId: [ticket.priorityId, Validators.required],
          statusId: [ticket.statusId, Validators.required],
          userId: [ticket.userId],
        });
      }
    });

    this.ticketFacade.getAllPriorities().subscribe((priorities) => {
      this.priorityOptions = priorities;
    });

    this.ticketFacade.getAllStatuses().subscribe((statuses) => {
      this.statusOptions = statuses;
    });

    this.ticketFacade.getAllTags().subscribe((tags) => {
      this.tagOptions = tags;
    });
  }

  onSubmit() {
    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();
      return;
    }

    const userIdString = localStorage.getItem('userId');

    if (!userIdString) {
      console.error('Kullanıcı ID’si alınamadı.');
      return;
    }
    const userId = Number(userIdString);

    this.ticketForm.patchValue({ userId: userId });

    const ticketUpdateRequest: TicketRequest = {
      ...this.ticketForm.value,
      userId: userId,
    };

    const ticketAdminUpdateRequest: TicketAdminUpdateRequest = {
      ...this.ticketForm.value,
      userId: userId,
    };
    this.ticketFacade
      .updateTicket(this.ticketId, ticketUpdateRequest);

    this.adminFacade
      .respondToTicket(this.ticketId, ticketAdminUpdateRequest)
      .subscribe();
    this.adminFacade
      .updatePriority(this.ticketId, ticketAdminUpdateRequest)
      .subscribe();
    this.adminFacade
      .updateStatus(this.ticketId, ticketAdminUpdateRequest)
      .subscribe();
    this.adminFacade
      .updateTag(this.ticketId, ticketAdminUpdateRequest)
      .subscribe();
    this.router.navigate(['/admin/ticket', this.ticketId]);
  }

  goToDashboard() {
    this.router.navigate(['/dashboard']);
  }
}
