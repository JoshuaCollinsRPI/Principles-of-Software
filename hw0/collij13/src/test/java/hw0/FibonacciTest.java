/**
 * This is part of HW0: Environment Setup and Java Introduction for CSCI-2600.
 */

package hw0;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * FibonacciTest is a glassbox test of the Fibonacci class.
 *
 * Recall that the Fibonacci sequence is a recursive
 * sequence where the first two terms of the sequence are 1 and all subsequent
 * terms are the sum of the previous two terms.
 *
 * Thus, the Fibonacci sequence starts out as 0, 1, 1, 2, 3, 5, 8, 13, ...
 * The first 0 in the sequence is considered the "0th" term,
 * so the indices that <code>hw0.Fibonacci</code> uses are 0-based.
 *
 * @see hw0.Fibonacci
 *
 */

public class FibonacciTest {

    private static Fibonacci fib = null;

    @BeforeAll
    public static void setupBeforeTests() throws Exception {
        fib = new Fibonacci();
    }

    /**
     * Tests that Fibonacci throws an IllegalArgumentException
     * for a negative number.
     */
    @Test()
    public void expectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> { fib.getFibTerm(-1); });
    }

    /**
     * Tests that Fibonacci throws no IllegalArgumentException
     * for zero or for a positive number.
     */
    @Test
    public void testThrowsIllegalArgumentException() {

        // test 0
        try {
            fib.getFibTerm(0);
        } catch (IllegalArgumentException ex) {
            fail("Threw IllegalArgumentException for 0 but 0 is nonnegative: "
                    + ex);
        }

        // test 1
        try {
            fib.getFibTerm(1);
        } catch (IllegalArgumentException ex) {
            fail("Threw IllegalArgumentException for 1 but 1 is nonnegative: "
                    + ex);
        }
    }

    /** Tests to see that Fibonacci returns the correct value for the base cases, n=0 and n=1 */
    @Test
    public void testBaseCase() {
        assertEquals(0, fib.getFibTerm(0), "getFibTerm(0)");
        assertEquals(1, fib.getFibTerm(1), "getFibTerm(1)");
    }

    /** Tests inductive cases of the Fibonacci sequence */
    @Test
    public void testInductiveCase() {  
           int[][] cases = new int[][] {
                { 2, 1 },
                { 3, 2 },
                { 4, 3 },
                { 5, 5 },
                { 6, 8 },
                { 7, 13 },
                { 8, 21 }
	   };       
	   for (int i = 0; i < cases.length; i++) {
	       assertEquals(cases[i][1], fib.getFibTerm(cases[i][0]), "getFibTerm(" + cases[i][0] + ")");
	   }
   }

    /** Tests to see that Fibonacci returns the correct value for a relatively large n */
    @Test
    public void testLargeN() {
        assertEquals(1548008755920L, fib.getFibTerm(60), "getFibTerm(60)");
    }
}
