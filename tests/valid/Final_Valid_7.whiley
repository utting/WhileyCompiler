// Test local variable initialisation
function id(int x) -> (int r)
ensures (r == 0) || (r == x):
    //
    final int y
    //
    switch(x):
        case 0:
            y = x        
        default:
            return 0
    //
    return y

public export method test():
    assert id(-1) == 0
    assert id(0) == 0
    assert id(1) == 0