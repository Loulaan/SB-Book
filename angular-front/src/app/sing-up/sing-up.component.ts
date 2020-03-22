import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../Services/auth.service";

@Component({
  selector: 'app-sing-up',
  templateUrl: './sing-up.component.html',
  styleUrls: ['./sing-up.component.less']
})
export class SingUpComponent implements OnInit {
  public form: FormGroup;
  public step = 1;

  constructor(private authService: AuthService) {
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

  onReg() {
    this.authService.signup(this.form.value).subscribe(() => {
        this.step = 2;
        console.log(this.step);
      },
      error => this.step = 2,
    );
  }
}
