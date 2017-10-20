// Test local variable initialisation
function id(int x) -> (int r)
ensures (r == 0) || (r == x):
    //
    final int y
    //
    if x < 0:
        y = x
    else:
        y = 0
    //
    return y

public export method test():
    assert id(-1) == -1
    assert id(0) == 0
    assert id(1) == 0