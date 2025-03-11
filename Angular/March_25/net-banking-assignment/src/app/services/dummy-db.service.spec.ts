import { TestBed } from '@angular/core/testing';

import { DummyDbService } from './dummy-db.service';

describe('DummyDbService', () => {
  let service: DummyDbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DummyDbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
