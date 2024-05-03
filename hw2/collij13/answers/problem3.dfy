method differences(arr: array?<int>) returns (diffs: array<int>)
    requires arr != null && arr.Length > 0;
    ensures diffs.Length == arr.Length - 1 && 
                   forall c :: (0 <= c < diffs.Length) ==> diffs[c] == arr[c + 1] - arr[c]
{
    diffs := new int[arr.Length - 1];
    var a := 0;

    while (a < diffs.Length)
        decreases diffs.Length - a // A decrementing since a increments within each loop
        invariant 0 <= a <= diffs.Length && diffs.Length == (arr.Length - 1) &&
                  forall b :: (0 <= b < a) ==> diffs[b] == arr[b + 1] - arr[b]
    {
        // used to find the difference of 
        diffs[a] := arr[a + 1] - arr[a];

        // used to increment a
        a := a + 1;
    }
}
