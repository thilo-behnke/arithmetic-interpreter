import { Injectable } from '@angular/core';
import { AST } from '../model/interpreter-result';

export type TreantTree = {
  text: { name: string };
  HTMLclass?: string;
  children: TreantTree[];
};

@Injectable({ providedIn: 'root' })
export class AstTreantConversionService {
  convertToTreant(ast: AST): TreantTree {
    return {
      text: { name: ast.value.value.toString() },
      HTMLclass: ast.leaf ? 'ast-leaf' : 'ast-node',
      children: ast.node
        ? ast.children.map(this.convertToTreant.bind(this))
        : [],
    };
  }
}
