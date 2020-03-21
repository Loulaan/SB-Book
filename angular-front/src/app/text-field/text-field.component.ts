import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-text-field',
  templateUrl: './text-field.component.html',
  styleUrls: ['./text-field.component.less']
})
export class TextFieldComponent implements OnInit {

  public animated = false;
  public form: FormGroup;

  constructor() {
    this.form = new FormGroup({
      userRequest: new FormControl(''),
    });
  }
  ngOnInit() {
    setTimeout(() => this.animated = true, 1000);
  }


 public animation() {
    this.animated = !this.animated;
  }
}
