import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-header-manu',
  templateUrl: './header-menu.component.html',
  styleUrls: ['./header-menu.component.less']
})
export class HeaderMenuComponent implements OnInit {
  @Input() isDarkPage = false;
  public showMain = true;

  constructor() { }

  ngOnInit() {
  }

}
