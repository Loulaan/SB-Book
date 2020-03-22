import { Component, OnInit } from '@angular/core';
import {SmartSearchService} from '../Services/smart-search.service';
import {SmartMatchInterface} from '../Interfaces/smartMatch.interface';
import {SmartMatchModel} from '../Interfaces/smartMatch.model';
import {Router} from "@angular/router";

@Component({
  selector: 'app-match-result',
  templateUrl: './match-result.component.html',
  styleUrls: ['./match-result.component.less']
})
export class MatchResultComponent implements OnInit {
  public bookArray: any;

  constructor(private smartSearchService: SmartSearchService, private router: Router) {
  }

  ngOnInit() {
    this.smartSearchService.resultSubject.subscribe((result) => this.bookArray = result);
  }

  public onClick(bookId = 1) {
    console.log('click');
    this.router.navigate([`/book/${bookId}`]);
  }
}
