import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { AdminFacade } from '../../store/admin.facade';
import { Status } from '../../../models/status.model';

@Component({
  selector: 'app-add-statuses',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-statuses.html',
  styleUrl: './add-statuses.css'
})
export class AddStatuses implements OnInit {
  statusForm: FormGroup;
  statuses$: Observable<Status[]>;

  constructor(
    private fb: FormBuilder,
    private adminFacade: AdminFacade
  ) {
    this.statusForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]]
    });
    this.statuses$ = this.adminFacade.statuses$;
  }

  ngOnInit(): void {
    this.adminFacade.refreshStatuses();
  }

  onSubmit(): void {
    if (this.statusForm.valid) {
      const statusRequest = {
        name: this.statusForm.value.name.trim()
      };
      
      this.adminFacade.createStatus(statusRequest).subscribe({
        next: () => {
          this.statusForm.reset();
        },
        error: (error) => {
          console.error('Status ekleme hatası:', error);
        }
      });
    }
  }

}
