import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InterpreterService } from './interpreter.service';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { InterpreterResult } from './model/interpreter-result';

@Component({
  selector: 'app-interpreter',
  styleUrls: ['./interpreter.component.scss'],
  template: `
    <div class="interpreter">
      <div class="interpreter__input">
        <interpreter-input
          class="interpreter__input__field"
          (expressionChange)="onExpressionChange($event)"
        ></interpreter-input>
      </div>
      <div class="interpreter__commands">
        <ng-container *ngFor="let command of commands | keyvalue">
          <interpreter-command
            [id]="command.value"
            [name]="command.value + ' (' + command.key + ')'"
            (execute)="handleCommandButton($event)"
          ></interpreter-command>
        </ng-container>
      </div>
      <interpreter-result
        [isLoading]="isLoading"
        [isError]="!!error"
        class="interpreter__result"
      >
        <ng-container *ngIf="interpreterResult">
          Result: {{ interpreterResult.result }}
        </ng-container>
        <ng-container *ngIf="commandResult">
          Command Result: {{ commandResult }}
        </ng-container>
        <ng-container *ngIf="error"> Error: {{ error | json }} </ng-container>
      </interpreter-result>
      <ng-container *ngIf="interpreterResult">
        <app-ast-visualizer [ast]="interpreterResult.ast"></app-ast-visualizer>
      </ng-container>
    </div>
  `,
})
export class InterpreterComponent {
  interpreterResult: InterpreterResult;
  commandResult: string;
  error: string | null = null;

  isLoading = false;

  commands = {
    ':var': 'GET_BINDINGS',
    ':clear': 'CLEAR_BINDINGS',
  };

  constructor(
    private interpreterService: InterpreterService,
    private snackBar: MatSnackBar
  ) {}

  private init() {
    this.interpreterResult = null;
    this.commandResult = null;
    this.error = null;
  }

  onExpressionChange(expression: string) {
    this.init();
    this.isLoading = true;

    if (expression.startsWith(':')) {
      this.handleCommand(this.commands[expression]);
    } else {
      this.handleExpression(expression);
    }
  }

  public handleCommandButton(commandName: string) {
    this.init();
    this.isLoading = true;

    this.handleCommand(commandName);
  }

  private handleCommand(commandName: string) {
    if (!commandName) {
      this.snackBar.open(
        `Illegal command encountered: ${commandName}. Allowed commands are :var and :clear.`
      );
    }
    this.interpreterService
      .executeCommand(commandName)
      .pipe(
        tap((result: string) => (this.commandResult = result)),
        catchError((error) => {
          this.error = error;
          return of(null);
        })
      )
      .subscribe(() => (this.isLoading = false));
  }

  private handleExpression(expression: string) {
    this.interpreterService
      .evaluateExpression(expression)
      .pipe(
        tap((result: InterpreterResult) => (this.interpreterResult = result)),
        catchError((error) => {
          this.error = error?.error || 'Unknown error';
          return of(null);
        })
      )
      .subscribe(() => (this.isLoading = false));
  }
}
