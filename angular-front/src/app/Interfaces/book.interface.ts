export interface IBook {
  id: number;
  imageUrl: string;
  title: string;
  description: string;
  price: string;
  author: string;
  publishingHouse: string;
}

export interface IBookSearch {
  bookList: IBook[];
}
