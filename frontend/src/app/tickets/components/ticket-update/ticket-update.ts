import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { TicketRequest } from '../../../models/ticket.model';
import { PriorityResponse } from '../../../models/priority.model';
import { StatusResponse } from '../../../models/status.model';
import { TagResponse } from '../../../models/tag.model';
import { TicketFacade } from '../../store/ticket.facade';
import { Router } from '@angular/router';
import { AuthService } from '../../../auth/auth.service';
import { Ticket } from '../../../models/ticket.model';
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-ticket-update',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './ticket-update.html',
  styleUrl: './ticket-update.css',
})
export class TicketUpdateComponent {
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
    private authService: AuthService
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

    const ticketRequest: TicketRequest = {
      ...this.ticketForm.value,
      userId: userId,
    };

    this.ticketFacade.updateTicket(this.ticketId, ticketRequest);
    this.router.navigate(['/ticket', this.ticketId]);
  }

  goToDashboard() {
    this.router.navigate(['/dashboard']);
  }
}
