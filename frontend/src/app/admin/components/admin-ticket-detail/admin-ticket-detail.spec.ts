import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTicketDetail } from './admin-ticket-detail';

describe('AdminTicketDetail', () => {
  let component: AdminTicketDetail;
  let fixture: ComponentFixture<AdminTicketDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTicketDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminTicketDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
