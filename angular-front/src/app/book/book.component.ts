import { Component, OnInit } from '@angular/core';
import {BookService} from '../Services/book.service';
import {ActivatedRoute} from '@angular/router';
import {IBook} from '../Interfaces/book.interface';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.less']
})
export class BookComponent implements OnInit {
  private id: number;
  public book: IBook;

  constructor(private bookService: BookService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => this.id = params.id);
    this.bookService.getSingleBook(this.id).subscribe((response) => this.book = response);
  }

}
