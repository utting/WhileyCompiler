type nat is (int n) where n >= 0
type natlist is nat[]

type nlt is nat | natlist

function g(int y) -> nlt:
    return y

public export method test() :
    g(-1)
