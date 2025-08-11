import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { AdminFacade } from '../../store/admin.facade';
import { Priority } from '../../../models/priority.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-priorities',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-priorities.html',
  styleUrl: './add-priorities.css',
})
export class AddPriorities implements OnInit {
  priorityForm: FormGroup;
  priorities$: Observable<Priority[]>;

  constructor(private fb: FormBuilder, private adminFacade: AdminFacade,
              private router: Router
  ) {
    this.priorityForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
    });
    this.priorities$ = this.adminFacade.priorities$;
  }

  ngOnInit(): void {
    this.adminFacade.refreshPriorities();
  }

  onSubmit(): void {
    if (this.priorityForm.valid) {
      const priorityRequest = {
        name: this.priorityForm.value.name.trim(),
      };

      this.adminFacade.createPriority(priorityRequest).subscribe({
        next: () => {
          this.priorityForm.reset();
        },
        error: (error) => {
          console.error('Öncelik ekleme hatası:', error);
        },
      });
    }
  }
  goBack() {
    this.router.navigate(['/admin-dashboard']);
  }
}
