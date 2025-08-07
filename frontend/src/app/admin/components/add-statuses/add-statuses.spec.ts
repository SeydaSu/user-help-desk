import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStatuses } from './add-statuses';

describe('AddStatuses', () => {
  let component: AddStatuses;
  let fixture: ComponentFixture<AddStatuses>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddStatuses]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddStatuses);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
