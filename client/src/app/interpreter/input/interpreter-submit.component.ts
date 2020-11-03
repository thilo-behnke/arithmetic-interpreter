import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'interpreter-submit',
  template: `
    <button
      mat-button
      mat-raised-button
      color="primary"
      style="margin-left: 10px"
      [disabled]="disabled"
      (click)="submit.emit()"
      type="submit"
    >
      submit
    </button>
  `,
})
export class InterpreterSubmitComponent {
  @Input() disabled: boolean;
  @Output() submit = new EventEmitter<void>();
}
