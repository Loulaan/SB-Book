import {SmartMatchInterface} from "./smartMatch.interface";

export class SmartMatchModel implements SmartMatchInterface {
  _id: string;
  _index: string;
  _score: number;
  _source: {
    actors: string[];
    author: {
      first_name: string;
      last_name: string;
      middle_name: string;
    }
    category: string[];
    features: string[];
    places: string[];
    summirize: string;
    title: string;
  };
  _type: string;

  constructor(props) {
    Object.assign(this, props);
  }

  public getAuthorName() {
    return this._source.author.last_name + this._source.author.first_name.slice(0, 1) + '.' + this._source.author.middle_name.slice(0, 1) + '.';
  }

}
