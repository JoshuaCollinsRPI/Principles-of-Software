method dutch(arr: array?<char>) returns (k: int)
    requires arr != null
    requires forall n :: 0 <= n < arr.Length ==> arr[n] == 'r' || arr[n] == 'b'
    ensures 0 <= k <= arr.Length
    ensures forall l :: 0 <= l < k ==> arr[l] == 'r'
    ensures forall m :: k <= m < arr.Length ==> arr[m] == 'b'
    modifies arr
    {
        k := 0;
    	var index := 0;

    	while (index < arr.Length)
            decreases arr.Length - index
            invariant 0 <= k <= index <= arr.Length
    	    invariant forall l :: 0 <= l < k ==> arr[l] == 'r'
            invariant forall m :: k <= m < index ==> arr[m] == 'b'
    	    invariant forall n :: index <= n < arr.Length ==> arr[n] == 'r' || arr[n] == 'b'
    	    {
       	        if arr[index] == 'r'
        	{
                    var temp := arr[index];
            	    arr[index] := arr[k];
    		    arr[k] := temp;

            	    k := k + 1;
        	}

                index := index + 1;
    	    }
    }