import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainPageComponent } from './main-page/main-page.component';
import { TextFieldComponent } from './text-field/text-field.component';
import { SearchComponent } from './search/search.component';
import { HeaderMenuComponent } from './header-menu/header-menu.component';
import { SingUpComponent } from './sing-up/sing-up.component';
import { SingInComponent } from './sing-in/sing-in.component';
import { PersonalPageComponent } from './personal-page/personal-page.component';
import { CartComponent } from './cart/cart.component';
import { SearchResultComponent } from './search-result/search-result.component';
import { BookComponent } from './book/book.component';
import { CartIconComponent } from './cart-icon/cart-icon.component';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    TextFieldComponent,
    SearchComponent,
    HeaderMenuComponent,
    SingUpComponent,
    SingInComponent,
    PersonalPageComponent,
    CartComponent,
    SearchResultComponent,
    BookComponent,
    CartIconComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
