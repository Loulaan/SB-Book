import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {AuthService} from '../Services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sing-in',
  templateUrl: './sing-in.component.html',
  styleUrls: ['./sing-in.component.less']
})
export class SingInComponent implements OnInit {
  public form: FormGroup;

  constructor(private authService: AuthService, private router: Router) { this.form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  }

  ngOnInit() {
  }

  public onClick() {
    this.authService.login(this.form.value).subscribe((response) => this.router.navigate(['/']));
  }
}
