import * from whiley.lang.*

type IntRealList is [int] | [real]

function f([int] xs) => [int]:
    return xs

method main(System.Console sys) => void:
    x = [1, 2, 3]
    ys = x
    sys.out.println(Any.toString(ys))
    x[0] = 1.23
    zs = f(x)
    sys.out.println(Any.toString(zs))
