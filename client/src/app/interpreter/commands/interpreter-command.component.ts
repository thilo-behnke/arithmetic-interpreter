import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'interpreter-command',
  template: `<button mat-button mat-raised-button (click)="execute.next(id)">
    {{ name }}
  </button>`,
})
export class InterpreterCommandComponent {
  @Input() id: string;
  @Input() name: string;
  @Output() execute = new EventEmitter<string>();
}
