/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.awt.Color;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * BoxTest is a glassbox test of the Box class.
 *
 * Recall that like a BallContainer, the Box is a container for Balls and you
 * can only put a Ball into a Box once. After you put the Ball into
 * the Box, further attempts to do so will fail, since the Ball is
 * already in the Box! Similarly, you cannot expect to remove a Ball
 * from a Box if it is not inside the Box.
 *
 * In addition, a Box differs from a BallContainer because it only has a finite
 * volume. As Balls get put into a Box, it gets filled up. Once a Box
 * is full, further attempts to put Balls into the Box will also fail.
 *
 * @see hw0.Ball
 * @see hw0.BallContainer
 * @see hw0.Box
 */
public class BoxTest {

    private static Box box = null;
    private static Ball[] b = null;

    private static final int NUM_BALLS_TO_TEST = 5;
    private static final int BOX_CAPACITY = NUM_BALLS_TO_TEST - 1;
    private static final double BALL_UNIT_VOLUME = 10.0;
    private static final double JUNIT_DOUBLE_DELTA = 0.0001;
    private static final int TRIES_FOR_BALLS_TEST = 3;
    private static final Color[] COLORS = {Color.BLACK, Color.BLUE,
    		Color.CYAN, new Color(255, 0, 0)};
    private static final int COLOR_COUNT = COLORS.length;    

    @BeforeAll
    public static void setupBeforeTests() throws Exception {

        assertTrue(NUM_BALLS_TO_TEST > 0,
        		"Test case error, you must test at least 1 Ball!");
        assertTrue(NUM_BALLS_TO_TEST > BOX_CAPACITY,
        		"This test case is set up assuming that the box cannot contain all the balls, please check and change parameters!");
        double box_volume = 0;

        // Let's create the balls we need.
        b = new Ball[NUM_BALLS_TO_TEST];
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            if (i < BOX_CAPACITY) {
                box_volume += (i + 1) * BALL_UNIT_VOLUME;
            }
            b[i] = new Ball((i + 1) * BALL_UNIT_VOLUME, COLORS[i % COLOR_COUNT]);
        }

        // Now, we create the box once we figure out how big a box we
        // need.
        box = new Box(box_volume);

    }

    /** Test to check that Box.add(Ball) is implemented correctly */
    @Test
    public void testAdd() {
        box.clear();
        for (int i = 0; i < BOX_CAPACITY; i++) {
            assertTrue(box.add(b[i]),
            		"Box.add(Ball) failed to add a new Ball!");
            assertFalse(box.add(b[i]),
            		"Box.add(Ball) seems to allow the same Ball to be added twice!");
            assertTrue(box.contains(b[i]),
            		"Box does not contain a ball after it is supposed to have been added!");
        }
        for (int i = BOX_CAPACITY; i < NUM_BALLS_TO_TEST; i++) {
            assertFalse(box.add(b[i]),
            		"Box.add(Ball) allows a Ball to be added even though it is already full! ");
        }
    }

    /** Test to check that Box.getBallsFromSmallest() is implemented correctly */
    @Test
    public void testGetBalls() {
        Random rnd = new Random();

        for (int k = 0; k < TRIES_FOR_BALLS_TEST; k++) {

            box.clear();

            // Let's put all the balls into a list.
            LinkedList<Ball> list = new LinkedList<Ball>();
            for (int i = 0; i < BOX_CAPACITY; i++) {
                list.add(b[i]);
            }

            // First we add the balls to the box in some random order.
            for (int i = 0; i < BOX_CAPACITY; i++) {
                box.add(list.remove(rnd.nextInt(list.size())));
            }

            int contentsSize = box.size();
            // Next we call the iterator and check that the balls come out
            // in the correct order.
            Iterator<Ball> it = box.getBallsFromSmallest();
            int count = 0;
            while (it.hasNext() && count < BOX_CAPACITY) {
                Ball ball = it.next();
                assertEquals(b[count], ball,
                		"Balls are not returned by Box.getBallsFromSmallest() iterator in the correct order");
                if (b[count] != ball) {
                    break;
                }
                count++;
            }
            assertEquals(BOX_CAPACITY, count,
            		"Box.getBallsFromSmallest() did not return all the balls");
            assertEquals(contentsSize, box.size(),
            		"The number of balls in the box was modified");
        }
    }

    /**
     * Test to check that Box.remove(Ball) is implemented
     * correctly. Depending on how <code>getBallsFromSmallest()</code>
     * is implemented, remove() might have to be overridden and this
     * test helps ensure that remove() is not broken in the process.
     */
    @Test
    public void testRemove() {
        box.clear();
        assertFalse(box.remove(b[0]),
        		"Box.remove(Ball) should fail because box is empty, but it didn't!");
        for (int i = 0; i < BOX_CAPACITY; i++) {
            box.clear();
            for (int j = 0; j < i; j++) {
                box.add(b[j]);
            }
            for (int j = 0; j < i; j++) {
                assertTrue(box.remove(b[j]),
                		"Box.remove(Ball) failed to remove a Ball that is supposed to be inside");
                assertFalse(box.contains(b[j]),
                		"Box still contains a ball after it is supposed to have been removed!");
            }
            for (int j = i; j < NUM_BALLS_TO_TEST; j++) {
                assertFalse(box.remove(b[j]),
                		"Box.remove(Ball) did not fail for a Ball that is not inside");
            }
        }
    }


    /** Test to check that Box.clear() is implemented correctly */
    @Test
    public void testClear() {
        box.clear();
        assertEquals(0, box.size(), "Box is not empty after being cleared!");
        box.add(b[0]);
        box.clear();
        assertEquals(0, box.size(), "Box is not empty after being cleared!");
    }

    /** Test to check that we can put a Ball into a Box */
    @Test
    public void testVolume() {
        box.clear();
        assertEquals(0, box.getVolume(), JUNIT_DOUBLE_DELTA,
        		"Volume of the empty Box is not zero!");
        for (int i = 0; i < BOX_CAPACITY; i++) {
            box.add(b[i]);
            assertEquals((i + 1) * (i + 2)*BALL_UNIT_VOLUME/2, box.getVolume(), JUNIT_DOUBLE_DELTA,
            		"Volume of the Box with "+(i+1)+" ball(s)");
        }
    }

    /** Test to check that size() returns the correct number. */
    @Test
    public void testSize() {
        box.clear();
        assertEquals(0, box.size(), "size() of the empty Box is not zero!");
        for (int i = 0; i < BOX_CAPACITY; i++) {
            box.add(b[i]);
            assertEquals(i + 1, box.size(), "size() of the Box with "+(i+1)+" ball(s)");
        }
    }

    /** Test to check that size() returns the correct number. */
    @Test
    public void testContains() {
        box.clear();
        for (int i = 0; i < BOX_CAPACITY; i++) {
            assertFalse(box.contains(b[i]),
            		"Empty Box seems to contain a ball!");
        }
        for (int i = 0; i < BOX_CAPACITY; i++) {
            box.add(b[i]);
            assertTrue(box.contains(b[i]),
            		"Box does not contain a Ball that is supposed to be inside!");
            for (int j = i + 1; j < BOX_CAPACITY; j++) {
                assertFalse(box.contains(b[j]),
                		"Box seems to contain a Ball that is not inside!");
            }
        }
    }

    /**
     * Test to check that iterator() is implemented correctly.
     */
    @Test
    public void testIterator() {
        Set<Ball> allBalls = new HashSet<Ball>();
        Set<Ball> seenBalls = new HashSet<Ball>();
        box.clear();
        for (Ball aBall : b) {
            box.add(aBall);
            allBalls.add(aBall);
        }
        int i = 0;
        for (Ball aBall : box) {
            assertTrue(allBalls.contains(aBall),
            		"Iterator returned a ball that isn't in the container: " + aBall);
            assertFalse(seenBalls.contains(aBall),
            		"Iterator returned the same ball twice: " + aBall);
            seenBalls.add(aBall);
            i++;
        }
        assertEquals(b.length - 1, i,
        		"BallContainer iterator did not return enough items!");
    }
}
