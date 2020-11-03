import {
  ComponentFactoryResolver,
  Directive,
  Input,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { MatSpinner } from '@angular/material/progress-spinner';

@Directive({
  selector: '[appLoading]',
})
export class LoadingDirective {
  private showsLoading = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private resolver: ComponentFactoryResolver
  ) {}

  @Input() set appLoading(condition: boolean) {
    if (condition && !this.showsLoading) {
      // Remove component, create spinner.
      this.viewContainer.clear();
      const factory = this.resolver.resolveComponentFactory(MatSpinner);
      this.viewContainer.createComponent(factory);
      this.showsLoading = true;
    } else if (!condition && this.showsLoading) {
      // Remove spinner, create component.
      this.viewContainer.clear();
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.showsLoading = false;
    }
  }
}
