import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketUpdate } from './ticket-update';

describe('TicketUpdate', () => {
  let component: TicketUpdate;
  let fixture: ComponentFixture<TicketUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketUpdate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketUpdate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
