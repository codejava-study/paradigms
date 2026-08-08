"use strict";

/*
 * @author Doschennikov Nikita (doschennicovn@gmail.com)
 */

// === OPERATIONS ===
function Expression(...elements) {
  this.elements = elements;
}
Expression.prototype.op = function (...args) {};
Expression.prototype.operationSymbol = "";
Expression.prototype.evaluate = function (...vars) {
  return this.op(...this.elements.map((el) => el.evaluate(...vars)));
};
Expression.prototype.toString = function () {
  return (
    this.elements.map((el) => el.toString()).join(" ") +
    " " +
    this.operationSymbol
  );
};
Expression.prototype.diff = function (varName) {};

// Base
function Add(a, b) {
  Expression.call(this, a, b);
  this.op = function (a, b) {
    return a + b;
  };
  this.operationSymbol = "+";
  this.diff = function (varName) {
    return new Add(a.diff(varName), b.diff(varName));
  };
}
Add.prototype = Object.create(Expression.prototype);

function Subtract(a, b) {
  Expression.call(this, a, b);
  this.op = function (a, b) {
    return a - b;
  };
  this.operationSymbol = "-";
  this.diff = function (varName) {
    return new Subtract(a.diff(varName), b.diff(varName));
  };
}
Subtract.prototype = Object.create(Expression.prototype);

function Multiply(a, b) {
  Expression.call(this, a, b);
  this.op = function (a, b) {
    return a * b;
  };
  this.operationSymbol = "*";
  this.diff = function (varName) {
    return new Add(
      new Multiply(a.diff(varName), b),
      new Multiply(a, b.diff(varName)),
    );
  };
}
Multiply.prototype = Object.create(Expression.prototype);

function Divide(a, b) {
  Expression.call(this, a, b);
  this.op = function (a, b) {
    return a / b;
  };
  this.operationSymbol = "/";
  this.diff = function (varName) {
    return new Divide(
      new Subtract(
        new Multiply(a.diff(varName), b),
        new Multiply(a, b.diff(varName)),
      ),
      new Multiply(b, b),
    );
  };
}
Divide.prototype = Object.create(Expression.prototype);

function Negate(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return -x;
  };
  this.operationSymbol = "negate";
  this.diff = function (varName) {
    return new Negate(x.diff(varName));
  };
}
Negate.prototype = Object.create(Expression.prototype);

// 3233
function Wrap(x, min, max) {
  Expression.call(this, x, min, max);
  this.op = function (x, min, max) {
    return x - Math.floor((x - min) / (max - min)) * (max - min);
  };
  this.operationSymbol = "wrap";
  this.diff = function (varName) {
    return new Subtract(
      x,
      new Multiply(
        new Floor(new Divide(new Subtract(x, min), new Subtract(max, min))),
        new Subtract(max, min),
      ),
    ).diff(varName);
  };
}
Wrap.prototype = Object.create(Expression.prototype);

function Floor(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.floor(x);
  };
  this.operationSymbol = "floor";
  this.diff = function (varName) {
    return new Const(0);
  };
}
Floor.prototype = Object.create(Expression.prototype);

// 3435
function SoftWrap(x, min, max, lambda) {
  Expression.call(this, x, min, max, lambda);
  this.op = function (x, min, max, lambda) {
    const a = (Math.PI * (x - min)) / (max - min);
    return (
      (Math.asin(Math.cos(a) * Math.tanh(lambda * -Math.sin(a))) *
        (max - min)) /
        Math.PI +
      (max + min) / 2
    );
  };
  this.operationSymbol = "softWrap";
  this.diff = function (varName) {
    let a = new Divide(
      new Multiply(new Const(Math.PI), new Subtract(x, min)),
      new Subtract(max, min),
    );

    return new Add(
      new Divide(
        new Multiply(
          new ArcSin(
            new Multiply(
              new Cos(a),
              new Tanh(new Multiply(lambda, new Negate(new Sin(a)))),
            ),
          ),
          new Subtract(max, min),
        ),
        new Const(Math.PI),
      ),
      new Divide(new Add(max, min), new Const(2)),
    ).diff(varName);
  };
}
SoftWrap.prototype = Object.create(Expression.prototype);

function ArcSin(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.asin(x);
  };
  this.operationSymbol = "asin";
  this.diff = function (varName) {
    return new Multiply(
      new Divide(
        new Const(1),
        new Sqrt(new Subtract(new Const(1), new Multiply(x, x))),
      ),
      x.diff(varName),
    );
  };
}
ArcSin.prototype = Object.create(Expression.prototype);

function Sqrt(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.sqrt(x);
  };
  this.operationSymbol = "sqrt";
  this.diff = function (varName) {
    return new Multiply(
      new Divide(new Const(1), new Multiply(new Const(2), new Sqrt(x))),
      x.diff(varName),
    );
  };
}
Sqrt.prototype = Object.create(Expression.prototype);

function Tanh(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.tanh(x);
  };
  this.operationSymbol = "tanh";
  this.diff = function (varName) {
    return new Divide(
      new Subtract(new Exp(x), new Exp(new Negate(x))),
      new Add(new Exp(x), new Exp(new Negate(x))),
    ).diff(varName);
  };
}
Tanh.prototype = Object.create(Expression.prototype);

function Exp(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.exp(x);
  };
  this.operationSymbol = "exp";
  this.diff = function (varName) {
    return new Multiply(new Exp(x), x.diff(varName));
  };
}
Exp.prototype = Object.create(Expression.prototype);

function Sin(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.sin(x);
  };
  this.operationSymbol = "sin";
  this.diff = function (varName) {
    return new Multiply(new Cos(x), x.diff(varName));
  };
}
Sin.prototype = Object.create(Expression.prototype);

function Cos(x) {
  Expression.call(this, x);
  this.op = function (x) {
    return Math.cos(x);
  };
  this.operationSymbol = "cos";
  this.diff = function (varName) {
    return new Multiply(new Negate(new Sin(x)), x.diff(varName));
  };
}
Cos.prototype = Object.create(Expression.prototype);

// 3637
function Power(a, b) {
  Expression.call(this, a, b);
  this.op = function (a, b) {
    return Math.pow(a, b);
  };
  this.operationSymbol = "pow";
  this.diff = function (varName) {
    const powerRuleTerm = new Multiply(
      new Multiply(b, new Power(a, new Subtract(b, new Const(1)))),
      a.diff(varName),
    );

    const bDiff = b.diff(varName);
    if (bDiff.evaluate(0, 0, 0) === 0) {
      return powerRuleTerm;
    }

    const lnA = new Log(new Const(Math.E), a);
    return new Add(
      powerRuleTerm,
      new Multiply(new Multiply(new Power(a, b), lnA), bDiff),
    );
  };
}
Power.prototype = Object.create(Expression.prototype);

function Log(a, b) {
  Expression.call(this, a, b);
  this.op = function (a, b) {
    return Math.log(Math.abs(b)) / Math.log(Math.abs(a));
  };
  this.operationSymbol = "log";
  this.diff = function (varName) {
    const lnA = new Log(new Const(Math.E), a);
    const lnB = new Log(new Const(Math.E), b);
    return new Divide(
      new Subtract(
        new Multiply(new Divide(b.diff(varName), b), lnA),
        new Multiply(lnB, new Divide(a.diff(varName), a)),
      ),
      new Multiply(lnA, lnA),
    );
  };
}
Log.prototype = Object.create(Expression.prototype);

function SumN(...args) {
  Expression.call(this, ...args);
  this.op = function (...args) {
    return args.reduce((a, b) => a + b, 0);
  };
  this.operationSymbol = "sum" + this.arity;
  this.diff = function (varName) {
    return args
      .map((a) => a.diff(varName))
      .reduce((a, b) => new Add(a, b), new Const(0));
  };
}
SumN.prototype = Object.create(Expression.prototype);
SumN.prototype.arity = "N";

function Sum1(a) {
  this.arity = 1;
  SumN.call(this, a);
}
Sum1.prototype = Object.create(SumN.prototype);

function Sum2(a, b) {
  this.arity = 2;
  SumN.call(this, a, b);
}
Sum2.prototype = Object.create(SumN.prototype);

function Sum3(a, b, c) {
  this.arity = 3;
  SumN.call(this, a, b, c);
}
Sum3.prototype = Object.create(SumN.prototype);

function Sum4(a, b, c, d) {
  this.arity = 4;
  SumN.call(this, a, b, c, d);
}
Sum4.prototype = Object.create(SumN.prototype);

function Sum5(a, b, c, d, e) {
  this.arity = 5;
  SumN.call(this, a, b, c, d, e);
}
Sum5.prototype = Object.create(SumN.prototype);

// 3839
function Gauss(a, b, c, x) {
  Expression.call(this, a, b, c, x);
  this.op = function (a, b, c, x) {
    return a * Math.exp(-((x - b) * (x - b)) / (2 * c * c));
  };
  this.operationSymbol = "gauss";
  this.diff = function (varName) {
    return new Multiply(
      a,
      new Exp(
        new Divide(
          new Negate(new Power(new Subtract(x, b), new Const(2))),
          new Multiply(new Const(2), new Power(c, new Const(2))),
        ),
      ),
    ).diff(varName);
  };
}
Gauss.prototype = Object.create(Expression.prototype);

function AvgN(...args) {
  Expression.call(this, ...args);
  this.op = function (...args) {
    return args.reduce((a, b) => a + b) / args.length;
  };
  this.operationSymbol = "avg" + this.arity;
  this.diff = function (varName) {
    let sum = args
      .map((a) => a.diff(varName))
      .reduce((a, b) => new Add(a, b), new Const(0));
    return new Divide(sum, new Const(args.length));
  };
}
AvgN.prototype = Object.create(Expression.prototype);
AvgN.prototype.arity = "N";

function Avg1(a) {
  this.arity = 1;
  AvgN.call(this, a);
}
Avg1.prototype = Object.create(AvgN.prototype);

function Avg2(a, b) {
  this.arity = 2;
  AvgN.call(this, a, b);
}
Avg2.prototype = Object.create(AvgN.prototype);

function Avg3(a, b, c) {
  this.arity = 3;
  AvgN.call(this, a, b, c);
}
Avg3.prototype = Object.create(AvgN.prototype);

function Avg4(a, b, c, d) {
  this.arity = 4;
  AvgN.call(this, a, b, c, d);
}
Avg4.prototype = Object.create(AvgN.prototype);

function Avg5(a, b, c, d, e) {
  this.arity = 5;
  AvgN.call(this, a, b, c, d, e);
}
Avg5.prototype = Object.create(AvgN.prototype);

const operations = {
  // Base
  "+": { apply: Add, arity: { min: 2, max: 2 } },
  "-": { apply: Subtract, arity: { min: 2, max: 2 } },
  "*": { apply: Multiply, arity: { min: 2, max: 2 } },
  "/": { apply: Divide, arity: { min: 2, max: 2 } },
  negate: { apply: Negate, arity: { min: 1, max: 1 } },

  // 3233
  wrap: { apply: Wrap, arity: { min: 3, max: 3 } },
  floor: { apply: Floor, arity: { min: 1, max: 1 } },

  // 3435
  softWrap: { apply: SoftWrap, arity: { min: 4, max: 4 } },
  sin: { apply: Sin, arity: { min: 1, max: 1 } },
  cos: { apply: Cos, arity: { min: 1, max: 1 } },
  tanh: { apply: Tanh, arity: { min: 1, max: 1 } },
  asin: { apply: ArcSin, arity: { min: 1, max: 1 } },
  sqrt: { apply: Sqrt, arity: { min: 1, max: 1 } },
  exp: { apply: Exp, arity: { min: 1, max: 1 } },

  // 3637
  pow: { apply: Power, arity: { min: 2, max: 2 } },
  log: { apply: Log, arity: { min: 2, max: 2 } },
  sum1: { apply: Sum1, arity: { min: 1, max: 1 } },
  sum2: { apply: Sum2, arity: { min: 2, max: 2 } },
  sum3: { apply: Sum3, arity: { min: 3, max: 3 } },
  sum4: { apply: Sum4, arity: { min: 4, max: 4 } },
  sum5: { apply: Sum5, arity: { min: 5, max: 5 } },

  // 3839
  gauss: { apply: Gauss, arity: { min: 4, max: 4 } },
  avg1: { apply: Avg1, arity: { min: 1, max: 1 } },
  avg2: { apply: Avg2, arity: { min: 2, max: 2 } },
  avg3: { apply: Avg3, arity: { min: 3, max: 3 } },
  avg4: { apply: Avg4, arity: { min: 4, max: 4 } },
  avg5: { apply: Avg5, arity: { min: 5, max: 5 } },
};

// === VARIABLES ===
const variables = ["x", "y", "z"];
function Variable(varName) {
  this.evaluate = function (...vars) {
    return vars[variables.indexOf(varName)];
  };
  this.toString = function () {
    return varName;
  };
  this.diff = function (diffVarName) {
    if (varName === diffVarName) {
      return new Const(1);
    } else {
      return new Const(0);
    }
  };
}

// === CONSTANTS ===
function Const(c) {
  this.evaluate = function (...vars) {
    return c;
  };
  this.toString = function () {
    return c.toString();
  };
  this.diff = function (varName) {
    return new Const(0);
  };
}

// === PARSING ===
let parse = (s) => {
  const tokens = s.split(" ").filter((t) => t.length != 0);
  let stack = [];

  for (const token of tokens) {
    if (token in operations) {
      let operation = operations[token];

      let args = [];
      for (let i = 0; i < operation.arity.max; i++) {
        args.push(stack.pop());
      }

      stack.push(new operation.apply(...args.reverse()));
    } else if (variables.includes(token)) {
      stack.push(new Variable(token));
    } else {
      stack.push(new Const(+token));
    }
  }

  return stack.pop();
};
