function permute(int[] xs) -> (int[] witness)
ensures |xs| == |witness|
ensures all { k in 0..|witness| | xs[k] == xs[witness[k]] }:
    //
    int i = 0
    int[] ws = [0; |xs|]
    //
    while i < |xs|
    where i >= 0 && |xs| == |ws|
    where all { j in 0..i | xs[j] == xs[ws[j]] }:
        ws[i] = i
        i = i + 1
    //
    return ws

method test():
    assume permute([1,2,3]) == [0,1,2]