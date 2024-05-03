method sumn(n: int) returns (t: int)
  requires n >= 0
  ensures t == n * (n + 1) / 2
{
  var i := 0;
  t := 0;

  while (i < n)
    invariant 0 <= i <= n
    invariant t == i * (i + 1) / 2
  {
    i := i + 1;
    t := t + i;
  }
}
