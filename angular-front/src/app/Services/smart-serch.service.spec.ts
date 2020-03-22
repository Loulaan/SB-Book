import { TestBed } from '@angular/core/testing';

import { SmartSearchService } from './smart-search.service';

describe('SmartSerchService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SmartSearchService = TestBed.get(SmartSearchService);
    expect(service).toBeTruthy();
  });
});
