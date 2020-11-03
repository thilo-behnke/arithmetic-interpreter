import { Component, Input, OnChanges, ViewEncapsulation } from '@angular/core';
import * as treant from 'treant-js';
import * as raphael from 'raphael';
import { AST } from '../model/interpreter-result';
import { AstTreantConversionService } from './ast-treant-conversion.service';

@Component({
  selector: 'app-ast-visualizer',
  template: `
    <div style="width: 100%; height: 100%">
      <div id="ast-container" class="chart"></div>
    </div>
  `,
  styleUrls: ['./ast-visualizer.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AstVisualizerComponent implements OnChanges {
  @Input()
  ast: AST;

  constructor(private astTreantConversionService: AstTreantConversionService) {}

  ngOnChanges() {
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
        node: { HTMLclass: 'ast-node' },
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

    new treant.Treant(treantConfig);
  }
}
