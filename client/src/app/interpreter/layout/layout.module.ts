import { ModuleWithProviders, NgModule } from '@angular/core';
import { ResizeService, WindowResizeService } from './resize.service';

@NgModule()
export class LayoutModule {
  static forRoot(): ModuleWithProviders<LayoutModule> {
    return {
      ngModule: LayoutModule,
      providers: [{ provide: ResizeService, useClass: WindowResizeService }],
    };
  }
}
