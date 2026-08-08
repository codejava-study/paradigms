"use strict";

const variables = ["x", "y", "z"];

let nAryOp =
  (op) =>
  (...fs) =>
  (...vars) =>
    op(...fs.map((f) => f(...vars)));

let add = nAryOp((a, b) => a + b);
let subtract = nAryOp((a, b) => a - b);
let multiply = nAryOp((a, b) => a * b);
let divide = nAryOp((a, b) => a / b);
let negate = nAryOp((a) => -a);

let variable =
  (varName) =>
  (...vars) =>
    vars[variables.indexOf(varName)];

let cnst = (c) => () => c;
