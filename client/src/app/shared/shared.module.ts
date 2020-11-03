import { NgModule } from '@angular/core';
import { LoadingDirective } from './loading.directive';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  imports: [MatProgressSpinnerModule],
  declarations: [LoadingDirective],
  exports: [LoadingDirective],
})
export class SharedModule {}
