/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.util.Set;
import java.awt.Color;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * BallContainerTest is a glassbox test of the BallContainer class.
 *
 * Recall that the BallContainer is a container for balls. However, you can only
 * put a Ball into a BallContainer once. After you put the Ball into the BallContainer,
 * further attempts to do so will fail, since the Ball is already in
 * the BallContainer! Similarly, you cannot expect to remove a Ball from a BallContainer
 * if it is not inside the BallContainer.
 *
 * @see hw0.Ball
 * @see hw0.BallContainer
 */
public class BallContainerTest {

    private static BallContainer ballcontainer = null;
    private static Ball[] b = null;

    private static final int NUM_BALLS_TO_TEST = 3;
    private static final double BALL_UNIT_VOLUME = 20.0;
    private static final double JUNIT_DOUBLE_DELTA = 0.0001;
    private static final Color[] COLORS = {Color.BLACK, Color.BLUE,
    		Color.CYAN, new Color(255, 0, 0)};
    private static final int COLOR_COUNT = COLORS.length;

    @BeforeAll
    public static void setupForTests() throws Exception {

    	assertTrue(NUM_BALLS_TO_TEST > 0, "Test case error, you must test at least 1 Ball!");

    	ballcontainer = new BallContainer();

        // Let's create the balls we need.
        b = new Ball[NUM_BALLS_TO_TEST];
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            b[i] = new Ball((i + 1) * BALL_UNIT_VOLUME, COLORS[i % COLOR_COUNT]);
        }
    }


    /** Test to check that BallContainer.add(Ball) is implemented correctly */
    @Test
    public void testAdd() {
        double containerVolume;
        ballcontainer.clear();
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            assertTrue(ballcontainer.add(b[i]), "BallContainer.add(Ball) failed to add a new Ball!");
            containerVolume = ballcontainer.getVolume();
            assertFalse(ballcontainer.add(b[i]), "BallContainer.add(Ball) seems to allow the same Ball to be added twice!");
            assertEquals(containerVolume, ballcontainer.getVolume(), JUNIT_DOUBLE_DELTA,
            		"BallContainer's volume has changed, but its contents have not!");
            assertTrue(ballcontainer.contains(b[i]),
            		"BallContainer does not contain a ball after it is supposed to have been added!");
        }
    }

    /** Test to check that BallContainer.remove(Ball) is implemented correctly */
    @Test
    public void testRemove() {
        ballcontainer.clear();
        double containerVolume;
        assertFalse(ballcontainer.remove(b[0]),
        		"BallContainer.remove(Ball) should fail because ballcontainer is empty, but it didn't!");
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            ballcontainer.clear();
            for (int j = 0; j < i; j++) {
                ballcontainer.add(b[j]);
            }
            for (int j = 0; j < i; j++) {
                assertTrue(ballcontainer.remove(b[j]),
                		"BallContainer.remove(Ball) failed to remove a Ball that is supposed to be inside");
                containerVolume = ballcontainer.getVolume();
                assertFalse(ballcontainer.contains(b[j]),
                		"BallContainer still contains a ball after it is supposed to have been removed!");
                assertEquals(containerVolume, ballcontainer.getVolume(), JUNIT_DOUBLE_DELTA,
                		"BallContainer's volume has changed, but its contents have not!");
            }
            for (int j = i; j < NUM_BALLS_TO_TEST; j++) {
                assertFalse(ballcontainer.remove(b[j]),
                		"BallContainer.remove(Ball) did not fail for a Ball that is not inside");
            }
        }
    }

    /**
     * Test to check that BallContainer.iterator() is implemented
     * correctly.
     */
    @Test
    public void testIterator() {
        Set<Ball> allBalls = new HashSet<Ball>();
        Set<Ball> seenBalls = new HashSet<Ball>();
        ballcontainer.clear();
        assertEquals(0, ballcontainer.size(),
        		"BallContainer is not empty after being cleared!");
        for (Ball aBall: b) {
            ballcontainer.add(aBall);
            allBalls.add(aBall);
        }
        int i = 0;
        for (Ball aBall: ballcontainer) {
            assertTrue(allBalls.contains(aBall),
            		"Iterator returned a ball that isn't in the container: " + aBall);
            assertFalse(seenBalls.contains(aBall),
            		"Iterator returned the same ball twice: " + aBall);
            seenBalls.add(aBall);
            i++;
        }
        assertEquals(i, b.length,
        		"BallContainer iterator did not return enough items!");
    }

    /**
     * Test that BallContainer.clear() is implemented correctly.
     */
    @Test
    public void testClear() {
        ballcontainer.clear();
        assertEquals(0, ballcontainer.size(),
        		"BallContainer is not empty after being cleared!");
        ballcontainer.add(b[0]);
        ballcontainer.clear();
        assertEquals(0, ballcontainer.size(),
        		"BallContainer is not empty after being cleared!");
    }

    /** Test that BallContainer.getVolume() is implemented correctly */
    @Test
    public void testVolume() {
        ballcontainer.clear();
        assertEquals(0, ballcontainer.getVolume(), JUNIT_DOUBLE_DELTA,
        		"Volume of an empty BallContainer is not zero!");
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            ballcontainer.add(b[i]);
            assertEquals((i + 1) * (i + 2)*BALL_UNIT_VOLUME / 2, ballcontainer.getVolume(), JUNIT_DOUBLE_DELTA,
            		"Volume of BallContainer with "+(i+1)+" ball(s)");
        }

    }
    
    /** Test that BallContainer.differentColors() is implemented correctly */
    @Test
    public void testDifferentColors() {
        ballcontainer.clear();
        assertEquals(0, ballcontainer.differentColors(), JUNIT_DOUBLE_DELTA,
        		"The number of different colors of an empty BallContainer is not zero!");
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            ballcontainer.add(b[i]);
            assertEquals(ballcontainer.size() > COLOR_COUNT ? COLOR_COUNT : ballcontainer.size(),
                         ballcontainer.differentColors(), JUNIT_DOUBLE_DELTA,
                         "The number of different colors of a BallContainer with " + (i + 1) + " ball(s)");
        }
    }
    
    /** Test that BallContainer.areSameColor() is implemented correctly */
    @Test
    public void testAreSameColor() {
        ballcontainer.clear();
        assertTrue(ballcontainer.areSameColor(),
        		"Balls in an empty BallContainer seem to have different colors!");
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            ballcontainer.add(b[i]);
            assertEquals(ballcontainer.size() > 1 ? false : true, ballcontainer.areSameColor(),
            		"All balls in a BallContainer with " + (i + 1) + " ball(s)"
            				+ "seem to have the same color");
        }
    }    

    /** Test that size() returns the correct number. */
    @Test
    public void testSize() {
        ballcontainer.clear();
        assertEquals(0, ballcontainer.size(), "size() of empty BallContainer is not zero!");
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            ballcontainer.add(b[i]);
            assertEquals(i + 1, ballcontainer.size(), "size() of BallContainer with "+(i+1)+" ball(s)");
        }
    }

    /** Test that contains() returns true if and only if the ball 
     * is contained in the container. */
    @Test
    public void testContains() {
        ballcontainer.clear();
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            assertFalse(ballcontainer.contains(b[i]), "Empty BallContainer seems to contain a ball!");
        }
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            ballcontainer.add(b[i]);
            assertTrue(ballcontainer.contains(b[i]), "BallContainer does not contain a Ball that is supposed to be inside!");
            for (int j = i + 1; j < NUM_BALLS_TO_TEST; j++) {
                assertFalse(ballcontainer.contains(b[j]), "BallContainer seems to contain a Ball that is not inside!");
            }
        }
    }

    /** Test that clear removes all balls. **/
    @Test
    public void testVolumeAfterClear() {
        ballcontainer.add(b[0]);
        ballcontainer.clear();
        assertEquals(0, ballcontainer.getVolume(), JUNIT_DOUBLE_DELTA,
        		"The volume of BallContainer after being cleared is not reset to 0!");
    }

}