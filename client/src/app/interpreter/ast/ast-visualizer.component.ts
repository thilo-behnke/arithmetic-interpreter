import {
  AfterViewInit,
  Component, ElementRef,
  Input,
  OnChanges,
  OnDestroy, Renderer2,
  SimpleChanges,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import * as treant from 'treant-js';
import * as raphael from 'raphael';
import {AST} from '../model/interpreter-result';
import {AstTreantConversionService} from './ast-treant-conversion.service';
import {ResizeService} from "../layout/resize.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-ast-visualizer',
  template: `
    <div style="width: 100%; height: 100%">
      <div #svgContainer id="ast-container" class="chart"></div>
    </div>
  `,
  styleUrls: ['./ast-visualizer.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AstVisualizerComponent implements OnChanges, AfterViewInit, OnDestroy {
  @Input()
  ast: AST;

  @ViewChild('svgContainer')
  svgContainer: ElementRef;

  private _destroy$ = new Subject();

  constructor(private astTreantConversionService: AstTreantConversionService, private resizeService: ResizeService, private elRef: ElementRef, private renderer: Renderer2) {
  }

  ngAfterViewInit(): void {
    this.resizeService.resize.pipe(takeUntil(this._destroy$)).subscribe(() => {
      const parentWidth = this.elRef.nativeElement.offsetParent.clientWidth;

      const childElements = this.elRef.nativeElement.children;
      for (const child of childElements) {
        this.renderer.removeChild(this.elRef.nativeElement, child);
      }
      this.renderer.setStyle(this.svgContainer.nativeElement, 'width', `${parentWidth}px`);
      this.renderAst();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.renderAst();
  }

  private renderAst(): void {
    // Required for treant-js to work...
    if (!(window as any).Raphael) {
      (window as any).Raphael = raphael;
    }

    const treantTree = this.astTreantConversionService.convertToTreant(
      this.ast
    );
    const treantConfig = {
      chart: {
        container: '#ast-container',
        node: {HTMLclass: 'ast-node'},
        nodeAlign: 'TOP',
        connectors: {
          type: 'curve',
          style: {
            'stroke-width': 2,
            'stroke-linecap': 'round',
            stroke: '#ccc',
          },
        },
      },
      nodeStructure: treantTree,
    };

    const ast = new treant.Treant(treantConfig);
  }

  ngOnDestroy(): void {
    this._destroy$.next();
    this._destroy$.complete();
  }
}
