define nat as int where $ >= 0
define pos as int where $ > 0

define expr as nat | (expr lhs, expr rhs)
define posExpr as pos | (posExpr lhs, posExpr rhs)

void f(posExpr e1):
    expr e2 = e1

void System::main([string] args):
    f((lhs:(lhs:1,rhs:2),rhs:1))
