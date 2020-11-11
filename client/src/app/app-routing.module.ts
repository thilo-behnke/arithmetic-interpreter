import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {InterpreterInputComponent} from './interpreter/input/interpreter-input.component';
import {interpreterRoutes} from './interpreter/interpreter.module';

const routes: Routes = [
  {
    path: '',
    children: interpreterRoutes
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
