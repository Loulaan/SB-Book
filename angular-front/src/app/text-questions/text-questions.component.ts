import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {SmartSearchService} from '../Services/smart-search.service';

@Component({
  selector: 'app-text-questions',
  templateUrl: './text-questions.component.html',
  styleUrls: ['./text-questions.component.less'],
  encapsulation: ViewEncapsulation.None,

})
export class TextQuestionsComponent implements OnInit {
  @Input() id: number;
  public question: string;
  constructor(private smartSearchService: SmartSearchService) { }

  ngOnInit() {
  }

  public onAsk() {
    console.log(this.question);
    const window = document.getElementsByClassName('answer_filed')[0];
    this.appendQuestion(window);

    const request = {id: this.id, question: this.question};
    this.smartSearchService.getAnswer(request).subscribe((response) => {
      console.log(response);
      this.appendAnswer(window, response);
    });
  }

  private appendQuestion(window) {
    const block = document.createElement('div');
    const question = document.createElement('div');
    block.classList.add('question_block');
    question.classList.add('question');
    question.innerHTML = this.question;
    block.appendChild(question);
    window.appendChild(block);
  }

  private appendAnswer(window, answer) {
    const block = document.createElement('div');
    const question = document.createElement('div');
    block.classList.add('answer_block');
    question.classList.add('answer');
    question.innerHTML = answer;
    block.appendChild(question);
    window.appendChild(block);
  }
}
