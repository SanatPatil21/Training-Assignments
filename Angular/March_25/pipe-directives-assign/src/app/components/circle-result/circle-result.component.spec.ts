import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CircleResultComponent } from './circle-result.component';

describe('CircleResultComponent', () => {
  let component: CircleResultComponent;
  let fixture: ComponentFixture<CircleResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CircleResultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CircleResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
