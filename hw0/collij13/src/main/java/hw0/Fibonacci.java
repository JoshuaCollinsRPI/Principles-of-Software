	/**
	 * This is part of HW0: Environment Setup and Java Introduction.
	 */
	package hw0;
	
	/**
	 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
	 *
	 * The first two terms of the Fibonacci sequence are 0 and 1,
	 * and each subsequent term is the sum of the previous two terms.
	 *
	 */
	
	import java.util.HashMap;
	
	public class Fibonacci {
	
	    /**
	     * Calculates the desired term in the Fibonacci sequence.
	     *
	     * @param n the index of the desired term; the first index of the sequence is 0
	     * @return the <var>n</var>th term in the Fibonacci sequence
	     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
	     */
		
		public static HashMap<Integer, Long> map = new HashMap<>();
		
	    public long getFibTerm(int n) {
	        if (n < 0) {
	            throw new IllegalArgumentException(n + " is negative");
	        } else if (n <= 1) {
	        	if(map.containsKey(n)){
	        		return n;
	        	}else {
	        		Long x = (long) n;
	        		map.put(n,x);
	        		return n;
	        	}
	        } else {
	        	if(map.containsKey(n)){
	        		return map.get(n);
	        	}else {
	        		long out = getFibTerm(n - 1) + getFibTerm(n - 2);
	        		map.put(n,out);
	        		return out;
	        	}
	        }
	    }
	}
