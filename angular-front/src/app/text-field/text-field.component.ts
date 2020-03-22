import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {SmartSearchService} from '../Services/smart-search.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-text-field',
  templateUrl: './text-field.component.html',
  styleUrls: ['./text-field.component.less']
})
export class TextFieldComponent implements OnInit {
  @ViewChild('textAreaElement', {static: true}) textAreaElement: ElementRef;
  public textAreaValue: string;


  constructor(private smartSearchService: SmartSearchService, private router: Router) {
    this.form = new FormGroup({
      query: new FormControl(''),
    });
  }

  public animated = false;
  public form: FormGroup;
  ngOnInit() {
    this.textAreaValue = this.smartSearchService.smartRequest;
    console.log(this.textAreaValue);
    setTimeout(() => this.animated = true, 1000);
  }


 public animation() {
    this.animated = !this.animated;
  }

  public sendQuery() {
    console.log('click');
    this.smartSearchService.getSmartSearchResult(this.form.value).subscribe((resp) =>
      this.router.navigate(['/match'])
    )
  }
}
