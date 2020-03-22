import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainPageComponent} from './main-page/main-page.component';
import {SearchResultComponent} from './search-result/search-result.component';
import {BookComponent} from './book/book.component';
import {SingInComponent} from './sing-in/sing-in.component';
import {SingUpComponent} from './sing-up/sing-up.component';
import {CartComponent} from './cart/cart.component';
import {PersonalPageComponent} from './personal-page/personal-page.component';
import {MatchResultComponent} from "./match-result/match-result.component";


const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: MainPageComponent,
  },
  {
    path: 'search',
    component: SearchResultComponent,
  },
  {
    path: 'book/:id',
    component: BookComponent,
  },
  {
    path: 'sign-in',
    component: SingInComponent,
  },
  {
    path: 'sign-up',
    component: SingUpComponent,
  },
  {
    path: 'cart',
    component: CartComponent,
  },
  {
    path: 'personal',
    component: PersonalPageComponent,
  },
  {
    path: 'match',
    component: MatchResultComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
