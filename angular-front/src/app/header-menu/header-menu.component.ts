import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from "../Services/auth.service";

@Component({
  selector: 'app-header-manu',
  templateUrl: './header-menu.component.html',
  styleUrls: ['./header-menu.component.less']
})
export class HeaderMenuComponent implements OnInit {
  @Input() isDarkPage = false;
  public showMain = true;
  public userName: string;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.userName.subscribe((name) => {
      if (name !== '') {
        this.userName = name;
      } else {
        this.userName = undefined;
      }
    });
  }

  logout() {
    this.authService.logout();
  }
}
