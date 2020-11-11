import { HostListener, Injectable } from '@angular/core';
import { fromEvent, Observable } from 'rxjs';
import { debounceTime, share, throttleTime } from 'rxjs/operators';

export abstract class ResizeService {
  abstract get resize(): Observable<Event>;
}

@Injectable()
export class WindowResizeService implements ResizeService {
  private _resize = fromEvent(window, 'resize').pipe(
    debounceTime(100),
    share()
  );

  get resize(): Observable<Event> {
    return this._resize;
  }
}
