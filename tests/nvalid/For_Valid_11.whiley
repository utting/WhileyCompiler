import println from whiley.lang.System

type listdict is [int] | {int=>int}

function f(listdict ls) => int:
    r = 0
    for l in ls:
        r = r + 1
    return r

method main(System.Console sys) => void:
    ls = [1, 2, 3, 4, 5]
    sys.out.println(f(ls))
    ls = {10=>20, 30=>40}
    sys.out.println(f(ls))