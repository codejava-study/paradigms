"use strict";

/*
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */

// === OPERATIONS ===
let op =
  (op) =>
  (...fs) =>
  (...vars) =>
    op(...fs.map((f) => f(...vars)));

// Base
let add = op((a, b) => a + b);
let subtract = op((a, b) => a - b);
let multiply = op((a, b) => a * b);
let divide = op((a, b) => a / b);
let negate = op((a) => -a);

// 3233
let sin = op((a) => Math.sin(a));
let cos = op((a) => Math.cos(a));

// 3435
let arcTan = op((a) => Math.atan(a));
let arcTan2 = op((a, b) => Math.atan2(a, b));

// 3637
let clamp = op((x, min, max) => Math.max(min, Math.min(x, max)));
let wrap = op(
  (x, min, max) => x - Math.floor((x - min) / (max - min)) * (max - min),
);
let argMinMaxN =
  (f) =>
  (...args) => {
    let res = 0;
    for (let i = 0; i < args.length; i++) {
      if (f(args[i], args[res]) > 0) {
        res = i;
      }
    }

    return res;
  };
let argMinN = argMinMaxN((a, b) => b - a);
let argMaxN = argMinMaxN((a, b) => a - b);
let argMin3 = op((a, b, c) => argMinN(a, b, c));
let argMax3 = op((a, b, c) => argMaxN(a, b, c));
let argMin5 = op((a, b, c, d, e) => argMinN(a, b, c, d, e));
let argMax5 = op((a, b, c, d, e) => argMaxN(a, b, c, d, e));

// 3839
let softClamp = op(
  (x, min, max, lambda) =>
    min + (max - min) / (1 + Math.exp(lambda * ((max + min) / 2 - x))),
);

const operations = {
  // Base
  "+": { apply: add, arity: 2 },
  "-": { apply: subtract, arity: 2 },
  "*": { apply: multiply, arity: 2 },
  "/": { apply: divide, arity: 2 },
  negate: { apply: negate, arity: 1 },

  // 3233
  sin: { apply: sin, arity: 1 },
  cos: { apply: cos, arity: 1 },

  // 3435
  atan: { apply: arcTan, arity: 1 },
  atan2: { apply: arcTan2, arity: 2 },

  // 3637
  clamp: { apply: clamp, arity: 3 },
  wrap: { apply: wrap, arity: 3 },
  argMin3: { apply: argMin3, arity: 3 },
  argMax3: { apply: argMax3, arity: 3 },
  argMin5: { apply: argMin5, arity: 5 },
  argMax5: { apply: argMax5, arity: 5 },

  // 3839
  softClamp: { apply: softClamp, arity: 4 },
};

// === VARIABLES ===
const variables = ["x", "y", "z"];
let variable =
  (varName) =>
  (...vars) =>
    vars[variables.indexOf(varName)];

// === CONSTANTS ===
let cnst = (c) => () => c;
const one = cnst(1);
const two = cnst(2);
const three = cnst(3);
const constants = {
  one: one,
  two: two,
  three: three,
};

// === PARSING ===
let parse = (s) => {
  const tokens = s.split(" ").filter((t) => t.length != 0);
  let stack = [];

  for (const token of tokens) {
    if (token in operations) {
      let operation = operations[token];

      let args = [];
      for (let i = 0; i < operation.arity; i++) {
        args.push(stack.pop());
      }

      stack.push(operation.apply(...args.reverse()));
    } else if (variables.includes(token)) {
      stack.push(variable(token));
    } else {
      if (token in constants) {
        stack.push(constants[token]);
      } else {
        stack.push(cnst(+token));
      }
    }
  }

  return stack.pop();
};
