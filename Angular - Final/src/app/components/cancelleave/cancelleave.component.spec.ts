import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelleaveComponent } from './cancelleave.component';

describe('CancelleaveComponent', () => {
  let component: CancelleaveComponent;
  let fixture: ComponentFixture<CancelleaveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CancelleaveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CancelleaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
