import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRespondTicket } from './admin-respond-ticket';

describe('AdminRespondTicket', () => {
  let component: AdminRespondTicket;
  let fixture: ComponentFixture<AdminRespondTicket>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRespondTicket]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRespondTicket);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
