import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-sing-up',
  templateUrl: './sing-up.component.html',
  styleUrls: ['./sing-up.component.less']
})
export class SingUpComponent implements OnInit {
  public form: FormGroup;

  constructor() {
    this.form = new FormGroup({
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
