import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {SmartMatchInterface} from '../Interfaces/smartMatch.interface';
import {map, tap} from 'rxjs/operators';
import {SmartMatchModel} from '../Interfaces/smartMatch.model';

@Injectable({
  providedIn: 'root'
})
export class SmartSearchService {
  private SEARCH_API = environment.SMART_SEARCH;
  public smartResponse: SmartMatchModel[] = [];
  public smartRequest: string;

  constructor(private http: HttpClient) { }

  public getSmartSearchResult(data: { query: string}) {
    this.smartRequest = data.query;
    return this.http.post<SmartMatchInterface[]>(this.SEARCH_API, data).pipe(
      map(resp => resp.map(item => new SmartMatchModel(item))),
      tap(resp => this.smartResponse = resp));
  }
}
