# About

An arithmetic interpreter that parses and evaluates arithmetic expressions. 

Understands expressions of the following grammar:

- expression := expression binary_operation expression | '-' expression | '(' expression ')' | variable_assignment | term | unary_function
- term := number | variable

- number := integer | decimal
- integer := '0' | ('1' - '9') ('0' - '9')*
- decimal := ( integer )? '.' ('0' - '9')*

- variable := ('a' - 'z' | 'A' - 'Z') ('a' - 'z' | 'A' - 'Z' | '0' - '9')*
- variable_assignment := variable '=' expression

- binary_operation := '+' | '-' | '*' | '/'
- unary_function := ('sqrt' | 'log' | 'sin' | 'cos') '(' expression ')'

# How to run

Run: `./run-docker.sh`  
Access web-client: `http://0.0.0.0:80`

# Demo

Run: `http://interpreter.whos-coding.com`
