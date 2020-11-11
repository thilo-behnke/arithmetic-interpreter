import { NgModule } from '@angular/core';
import { InterpreterComponent } from './interpreter.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  DefaultInterpreterService,
  InterpreterService,
} from './interpreter.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { AstVisualizerComponent } from './ast/ast-visualizer.component';
import { MatCardModule } from '@angular/material/card';
import { InterpreterResultComponent } from './result/interpreter-result.component';
import { SharedModule } from '../shared/shared.module';
import { InterpreterInputComponent } from './input/interpreter-input.component';
import { InterpreterSubmitComponent } from './input/interpreter-submit.component';
import { InterpreterCommandComponent } from './commands/interpreter-command.component';
import { MatOptionModule } from '@angular/material/core';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

export const interpreterRoutes: Routes = [
  { path: '', component: InterpreterComponent },
];

@NgModule({
  imports: [
    RouterModule.forChild(interpreterRoutes),
    FormsModule,
    CommonModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    HttpClientModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatOptionModule,
    MatAutocompleteModule,
    SharedModule,
  ],
  exports: [InterpreterComponent],
  declarations: [
    InterpreterComponent,
    AstVisualizerComponent,
    InterpreterResultComponent,
    InterpreterInputComponent,
    InterpreterCommandComponent,
  ],
  providers: [
    { provide: InterpreterService, useClass: DefaultInterpreterService },
  ],
})
export class InterpreterModule {}
