import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {SmartMatchInterface} from '../Interfaces/smartMatch.interface';
import {map, tap} from 'rxjs/operators';
import {SmartMatchModel} from '../Interfaces/smartMatch.model';
import {BehaviorSubject} from 'rxjs';
import {IBook, IBookSearch} from '../Interfaces/book.interface';

@Injectable({
  providedIn: 'root'
})
export class SmartSearchService {
  private API_URL = environment.API_URL;
  public smartResponse: SmartMatchModel[] = [];
  public smartRequest: string;
  public resultSubject$: BehaviorSubject<SmartMatchModel[]> = new BehaviorSubject<SmartMatchModel[]>(null);
  public simpleSearchSubject$: BehaviorSubject<IBook[]> = new BehaviorSubject<IBook[]>(null);

  constructor(private http: HttpClient) { }

  get resultSubject() {
    return this.resultSubject$.asObservable();
  }

  get simpleSearchSubject() {
    return this.simpleSearchSubject$.asObservable();
  }

  public getSmartSearchResult(data: { query: string}) {
    this.smartRequest = data.query;
    return this.http.post<SmartMatchInterface[]>(`${this.API_URL}/search/smart_search`, data).pipe(
      map(resp => resp.map(item => new SmartMatchModel(item))),
      tap(resp => this.resultSubject$.next(resp)));
  }

  public getSimpleSearch(query) {
    console.log(encodeURI(`${this.API_URL}/books/search?query=${query}`));
    return this.http.get<IBookSearch>(`${this.API_URL}/books/search?query=${query}`).pipe(
      tap(result => this.simpleSearchSubject$.next(result.bookList)));
  }

  public getRandomBooks() {
    return this.http.get<IBookSearch>(`${this.API_URL}/books/random`).pipe(
      tap(result => this.simpleSearchSubject$.next(result.bookList)));
  }
}
