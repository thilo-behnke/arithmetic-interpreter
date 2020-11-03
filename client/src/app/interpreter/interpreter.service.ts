import { Injectable } from '@angular/core';
import { map, shareReplay, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import {
  InterpreterBindings,
  InterpreterResult,
} from './model/interpreter-result';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from '../../environments/environment';

export abstract class InterpreterService {
  abstract executeCommand(commandName: string): any;

  abstract evaluateExpression(
    expression: string
  ): Observable<InterpreterResult>;

  abstract get bindings$(): Observable<string[]>;
}

@Injectable()
export class DefaultInterpreterService implements InterpreterService {
  private bindingsSubject$ = new Subject<InterpreterBindings>();
  private _bindings$ = this.bindingsSubject$.asObservable().pipe(
    map((bindings: Array<{ [variableName: string]: string }>) => {
      return bindings.reduce((acc, val) => {
        return [...acc, ...Object.keys(val)];
      }, []);
    }),
    shareReplay(1)
  );

  constructor(private http: HttpClient) {
    // Fetch bindings on startup.
    this.executeCommand('GET_BINDINGS').subscribe();
  }

  executeCommand(commandName: string) {
    return this.http
      .get(`${environment.serverUrl}interpreter/command/${commandName}`)
      .pipe(
        tap((result: any) => {
          if (commandName === 'GET_BINDINGS') {
            this.bindingsSubject$.next(result);
          }
        }),
        map((result: any) => {
          if (commandName === 'GET_BINDINGS') {
            if (!result.length) {
              return 'No bindings set.';
            }
            return (result as { [key: string]: number }[])
              .reduce(
                (acc, binding) => [...acc, ...Object.entries(binding)],
                []
              )
              .map(([key, value]) => `${key}=${value}`)
              .join(' ');
          } else {
            return result?.message as string;
          }
        })
      );
  }

  get bindings$(): Observable<string[]> {
    return this._bindings$;
  }

  evaluateExpression(expression: string) {
    return this.http
      .post<InterpreterResult>(
        `${environment.serverUrl}interpreter/evaluate`,
        expression
      )
      .pipe(tap((result) => this.bindingsSubject$.next(result.bindings)));
  }
}
