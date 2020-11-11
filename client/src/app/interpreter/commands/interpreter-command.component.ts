import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'interpreter-command',
  styleUrls: ['./interpreter-command.component.scss'],
  template: `<button
    class="command"
    mat-button
    mat-raised-button
    [color]="color"
    (click)="execute.next(id)"
  >
    <div class="command__content">
      <span class="material-icons">{{ icon }}</span>
      <span class="command__content__text">{{ name }}</span>
    </div>
  </button>`,
})
export class InterpreterCommandComponent {
  @Input() id: string;
  @Input() name: string;
  @Input() icon: string;
  @Input() color: any;
  @Output() execute = new EventEmitter<string>();
}
