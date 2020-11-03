export type AST = {
  value: { type: string; value: string | number };
  children: AST[];
  node: boolean;
  leaf: boolean;
};

export type InterpreterBindings = Array<{ [varName: string]: string }>;

export type InterpreterResult = {
  result: number;
  ast: AST;
  bindings: InterpreterBindings;
};
