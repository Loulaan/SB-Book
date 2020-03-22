import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TextQuestionsComponent } from './text-questions.component';

describe('TextQuestionsComponent', () => {
  let component: TextQuestionsComponent;
  let fixture: ComponentFixture<TextQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TextQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
