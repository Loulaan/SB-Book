import { Component, OnInit } from '@angular/core';
import {SmartSearchService} from '../Services/smart-search.service';
import {SmartMatchInterface} from '../Interfaces/smartMatch.interface';
import {SmartMatchModel} from '../Interfaces/smartMatch.model';

@Component({
  selector: 'app-match-result',
  templateUrl: './match-result.component.html',
  styleUrls: ['./match-result.component.less']
})
export class MatchResultComponent implements OnInit {
  public bookArray: SmartMatchModel[];

  constructor(private smartSearchService: SmartSearchService) {
  }

  ngOnInit() {
    this.bookArray = this.smartSearchService.smartResponse;
    console.log(this.bookArray);
  }
}
