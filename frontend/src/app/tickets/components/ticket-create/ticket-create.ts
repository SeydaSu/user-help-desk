import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TicketFacade } from '../../store/ticket.facade';
import { Tag, Priority, Status } from '../../store/ticket.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ticket-create',
  standalone: true,    
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './ticket-create.html',
  styleUrls: ['./ticket-create.css']
})
export class TicketCreateComponent implements OnInit {
  ticketForm!: FormGroup;
  priorities = [
    { id: 1, label: 'Düşük' },
    { id: 2, label: 'Orta' },
    { id: 3, label: 'Yüksek' },
  ];
  tags: Tag[] = [
    { id: 1, name: 'Teknik' },
    { id: 2, name: 'Ödeme' },
    { id: 3, name: 'Hizmet' },
  ]; // admin backend hazır olunca API'den alınacak
  status = [
    { id: 1, label: 'Open' },
    { id: 2, label: 'In-progress'},
    { id: 3, label: 'Closed'}
    
  ]

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
    });
  }

  showSuccessMessage = false;

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
      // alert veya error handling yapılabilir
    }
  });
}


  goToDashboard(){
    this.router.navigate(['/dashboard']);
  }


}
