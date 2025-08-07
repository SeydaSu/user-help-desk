import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { AdminFacade } from '../../store/admin.facade';
import { Tag } from '../../../models/tag.model';

@Component({
  selector: 'app-add-tags',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-tags.html',
  styleUrl: './add-tags.css'
})
export class AddTags implements OnInit {
  tagForm: FormGroup;
  tags$: Observable<Tag[]>;

  constructor(
    private fb: FormBuilder,
    private adminFacade: AdminFacade
  ) {
    this.tagForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]]
    });
    this.tags$ = this.adminFacade.tags$;
  }

  ngOnInit(): void {
    this.adminFacade.refreshTags();
  }

  onSubmit(): void {
    if (this.tagForm.valid) {
      const tagRequest = {
        name: this.tagForm.value.name.trim()
      };
      
      this.adminFacade.createTag(tagRequest).subscribe({
        next: () => {
          this.tagForm.reset();
        },
        error: (error) => {
          console.error('Tag ekleme hatası:', error);
        }
      });
    }
  }
}