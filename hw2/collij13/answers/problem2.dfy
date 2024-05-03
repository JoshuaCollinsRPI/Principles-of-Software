method loopysqrt(n:int) returns (root:int)
    ensures root*root == n ||  root == -1
{
    root := 0;
    var a := n;
    while (a > 0)
        decreases a
        invariant 0 <= root && n >= a && a == n - (root * root)
    {
        root := root + 1;
        a := a - (2 * root - 1);
    }
    if (n != root * root) {
        root := -1;
    }
}