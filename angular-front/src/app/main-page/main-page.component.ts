import { Component, OnInit } from '@angular/core';
import {SmartSearchService} from '../Services/smart-search.service';
import {IBook} from '../Interfaces/book.interface';
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.less']
})
export class MainPageComponent implements OnInit {
  public books: IBook[];

  constructor(private smartSearchService: SmartSearchService, private router: Router) { }

  ngOnInit() {
      this.smartSearchService.getRandomBooks().subscribe();
      this.smartSearchService.simpleSearchSubject.subscribe((result) => this.books = result);
  }


  private onBookClick(id) {
    this.router.navigate([`/book/${id}`]);
  }
}
