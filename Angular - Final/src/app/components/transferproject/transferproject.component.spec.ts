import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferprojectComponent } from './transferproject.component';

describe('TransferprojectComponent', () => {
  let component: TransferprojectComponent;
  let fixture: ComponentFixture<TransferprojectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransferprojectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferprojectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
