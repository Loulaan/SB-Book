import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-sing-in',
  templateUrl: './sing-in.component.html',
  styleUrls: ['./sing-in.component.less']
})
export class SingInComponent implements OnInit {
  public form: FormGroup;

  constructor() { this.form = new FormGroup({
    email: new FormControl(''),
    userName: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    password: new FormControl(''),
  });
  }

  ngOnInit() {
  }

}
