import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { BehaviorSubject, combineLatest, Observable, of, Subject } from 'rxjs';
import {
  catchError,
  debounceTime,
  filter,
  map,
  takeUntil,
  tap,
} from 'rxjs/operators';
import { InterpreterService } from '../interpreter.service';

@Component({
  selector: 'interpreter-input',
  template: `
    <mat-form-field style="width: 100%">
      <mat-label>2 + 3 * (10 - log(200))</mat-label>
      <input
        matInput
        type="text"
        [(ngModel)]="expression"
        [matAutocomplete]="auto"
      />
      <mat-autocomplete #auto="matAutocomplete">
        <mat-option *ngFor="let binding of bindings$ | async" [value]="binding">
          {{ binding }}
        </mat-option>
      </mat-autocomplete>
      <button
        mat-button
        *ngIf="expression"
        matSuffix
        mat-icon-button
        aria-label="Clear"
        (click)="expression = ''"
      >
        <mat-icon>close</mat-icon>
      </button>
    </mat-form-field>
  `,
})
export class InterpreterInputComponent implements OnInit, OnDestroy {
  @Output() expressionChange = new EventEmitter<string>();

  private destroy$ = new Subject();
  private expressionSubject$ = new BehaviorSubject<string>('');
  private expression$ = this.expressionSubject$.asObservable().pipe(
    takeUntil(this.destroy$),
    filter((val) => !!val)
  );

  private expressionOutput$ = this.expression$.pipe(
    debounceTime(800),
    tap((val) => this.expressionChange.next(val)),
    catchError(() => of(null))
  );

  public bindings$: Observable<string[]>;

  @Input() get expression() {
    return this.expressionSubject$.getValue();
  }

  set expression(expression: string) {
    this.expressionSubject$.next(expression);
  }

  constructor(public interpreterService: InterpreterService) {}

  ngOnInit(): void {
    this.expressionOutput$.subscribe();

    this.bindings$ = combineLatest([
      this.expression$,
      this.interpreterService.bindings$,
    ]).pipe(
      map(([expression, bindings]) => {
        return bindings.filter((binding) => binding.startsWith(expression));
      })
    );
  }

  ngOnDestroy(): void {
    this.destroy$.complete();
  }
}
