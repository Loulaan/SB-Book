import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {IBook} from '../Interfaces/book.interface';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private APP_URL = environment.API_URL;

  constructor(private http: HttpClient) { }

  public getSingleBook(id) {
    return this.http.get<IBook>(`${this.APP_URL}/books/${id}`);
  }
}
