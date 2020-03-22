import { Component, OnInit } from '@angular/core';
import {SmartSearchService} from '../Services/smart-search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.less']
})
export class SearchComponent implements OnInit {
  public query: string;

  constructor(private smartSearchService: SmartSearchService) { }

  ngOnInit() {}

  public onSearch() {
    console.log('search', this.query);
    this.smartSearchService.getSimpleSearch(this.query).subscribe();
  }
}
