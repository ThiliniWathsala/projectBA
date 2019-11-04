import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeAssignedComponent } from './change-assigned.component';

describe('ChangeAssignedComponent', () => {
  let component: ChangeAssignedComponent;
  let fixture: ComponentFixture<ChangeAssignedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeAssignedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeAssignedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
