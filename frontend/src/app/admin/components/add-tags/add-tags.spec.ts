import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTags } from './add-tags';

describe('AddTags', () => {
  let component: AddTags;
  let fixture: ComponentFixture<AddTags>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddTags]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddTags);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
