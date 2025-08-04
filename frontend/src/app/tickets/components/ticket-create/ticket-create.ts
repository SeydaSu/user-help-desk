import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TicketFacade } from '../../store/ticket.facade';
import { CommonModule } from '@angular/common';
import { PriorityResponse } from '../../../models/priority.model';
import { StatusResponse } from '../../../models/status.model';
import { Tag, TagResponse } from '../../../models/tag.model';

@Component({
  selector: 'app-ticket-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './ticket-create.html',
  styleUrls: ['./ticket-create.css']
})
export class TicketCreateComponent implements OnInit {
  ticketForm!: FormGroup;

  priorityOptions: PriorityResponse[] = [];
  statusOptions: StatusResponse[] = [];
  tagOptions: TagResponse[] = [];

  showSuccessMessage = false;

  constructor(
    private fb: FormBuilder,
    private ticketFacade: TicketFacade,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ticketForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]],
      tagId: [null, Validators.required],
      priorityId: [null, Validators.required],
      statusId: [null, Validators.required]
    });

    // Admin panelinden gelen dinamik seçenekleri al
    this.ticketFacade.getAllPriorities().subscribe(priorities => {
      console.log('Gelen priority verisi:', priorities); 
      this.priorityOptions = priorities;
    });

    this.ticketFacade.getAllStatuses().subscribe(statuses => {
      this.statusOptions = statuses;
    });

    this.ticketFacade.getAllTags().subscribe(tags => {
       this.tagOptions = tags;
      
    });
    
  }

  onSubmit() {
    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();
      return;
    }

    this.ticketFacade.createTicket(this.ticketForm.value).subscribe({
      next: () => {
        this.showSuccessMessage = true;
        setTimeout(() => {
          this.showSuccessMessage = false;
          this.router.navigate(['/dashboard']);
        }, 1200);
      },
      error: () => {
        // error handling
      }
    });
  }

  goToDashboard() {
    this.router.navigate(['/dashboard']);
  }
}
