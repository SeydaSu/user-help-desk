import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPriorities } from './add-priorities';

describe('AddPriorities', () => {
  let component: AddPriorities;
  let fixture: ComponentFixture<AddPriorities>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPriorities]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPriorities);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
