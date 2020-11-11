import {Component, HostListener} from '@angular/core';
import {ResizeService} from "./interpreter/layout/resize.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'client';

  routes = [
    { path: '/', name: 'Arithmetic Interpreter', icon: 'calculate' }
  ];

}
