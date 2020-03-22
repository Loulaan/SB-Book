export interface SmartMatchInterface {
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
}
