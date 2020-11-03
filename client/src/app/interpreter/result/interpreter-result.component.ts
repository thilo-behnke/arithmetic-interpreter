import { Component, Input } from '@angular/core';

@Component({
  selector: 'interpreter-result',
  template: `
    <div style="display: flex; justify-content: center">
      <mat-card
        *appLoading="isLoading"
        style="flex: 1 1 auto; margin-bottom: 10px"
        [class.mat-warn-bg]="isError"
      >
        <ng-content></ng-content>
      </mat-card>
    </div>
  `,
})
export class InterpreterResultComponent {
  @Input()
  isLoading: boolean;
  @Input()
  isError: boolean;
}
