/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.lang.Iterable;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Collections;

/**
 * This is a container can be used to contain balls.
 * A given ball may only appear in a BallContainer once.
 */
public class BallContainer implements Iterable<Ball> {

    // Contents of the BallContainer.
    private Set<Ball> contents;

    /**
     * Constructor that creates a new ball container.
     */
    public BallContainer() {
        contents = new LinkedHashSet<Ball>();
    }

    /**
     * Implements the Iterable interface for this container.
     * @return an Iterator over the Ball objects contained
     * in this container
     */
    public Iterator<Ball> iterator() {
        // If we just returned the iterator of "contents", a client
        // could call the remove() method on the iterator and modify
	// it behind our backs.  Instead, we wrap contents in an
	// "unmodifiable set"; calling remove() on this iterator
	// throws an exception.  This is an example of avoiding
	// "representation exposure."  You will learn more about this
	// concept later in the course.
	return Collections.unmodifiableSet(contents).iterator();
    }

    /**
     * Adds a ball to the container. This method returns <tt>true</tt>
     * if the ball was successfully added to the container, i.e., the ball was
     * not already in the container. Of course, you are allowed to put
     * a Ball into a container only once. Hence, this method returns
     * <tt>false</tt>, if the ball is already in the container.
     * @param b ball to be added
     * @return true if the ball was successfully added to the container,
     * i.e., the ball was not already in the container. Returns false, if the ball is
     * already in the container
     */
    public boolean add(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
    	if(this.contents.contains(b)) {
    		return false;
    	}else {
    		this.contents.add(b);
    		return true;
    	}
    }

    /**
     * Removes a ball from the container. This method returns
     * <tt>true</tt> if the ball was successfully removed from the
     * container, i.e., the ball was actually in the container. You cannot
     * remove a ball if it is not already in the container, therefore in this
     * case the method returns <tt>false</tt>.
     * @param b ball to be removed
     * @return true if the ball was successfully removed from the container,
     * i.e., the ball was actually in the container. Returns false, if the ball is not
     * in the container
     */
    public boolean remove(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
    	if(this.contents.contains(b)) {
    		this.contents.remove(b);
    		return true;
    	}else {
    		return false;
    	}
    }

    /**
     * Each ball has a volume. This method returns the total volume of
     * all balls in the container.
     * @return the volume of the contents of the container
     */
    public double getVolume() {
        // Your code goes here.  Remove the exception after you're done.
    	
    	
    	 Iterator<Ball> temp = this.contents.iterator();
    	 
    	 float total_volume = 0;
    	 
    	 while(temp.hasNext()){
    		 Ball temp_ball = temp.next();
    	     total_volume += temp_ball.getVolume();
    	 }
    	 
    	 return total_volume;
    }

    /**
     * Returns the number of balls in this container.
     * @return the number of balls in this container
     */
    public int size() {
        // Your code goes here.  Remove the exception after you're done.
	   	 return this.contents.size();
	    }
    
    /**
     * Returns the number of different colors for the balls in this container.
     * @return the number of different colors for the balls in this container
     */
    public int differentColors() {
        // Your code goes here.  Remove the exception after you're done.
    	
    	Set<String> temp = new LinkedHashSet<String>();
    	Iterator<Ball> it = this.contents.iterator();
    	
	   	 while(it.hasNext()){
	   		 Ball x = it.next();
	   	     temp.add(String.valueOf(x.getColor()));
	   	 }
	   	 
	   	 return temp.size();
    }
    
    /**
     * Returns true if all balls in this container have the same color, 
     *   otherwise returns false.
     * @return true if all balls in this container have the same color, 
     *   otherwise returns false
     */
    public boolean areSameColor() {
        // Your code goes here.  Remove the exception after you're done.
    	
    	Iterator<Ball> it = this.contents.iterator();
    	if(!it.hasNext()) {
    		return true;
    	}else {
        	Ball x = it.next();
    	   	 while(it.hasNext()){
    	   		 Ball y = it.next();
    	   	     if (!x.getColor().equals(y.getColor())) {
    	   	    	 return false;
    	   	     }
    	   	 }
    	   	 return true;
    	}
    }

    /**
     * Empties the container, i.e., removes all its contents.
     */
    public void clear() {
        // Your code goes here.  Remove the exception after you're done.
        this.contents = new LinkedHashSet<Ball>();
    }

    /**
     * This method returns <tt>true</tt> if this container contains
     * the specified ball. It will return <tt>false</tt> otherwise.
     * @param b ball to be checked if it is in the container
     * @return true if this container contains the specified ball. Returns
     * false otherwise.
     */
    public boolean contains(Ball b) {
        // Your code goes here.  Remove the exception after you're done.
    	if(this.contents.contains(b)) {
    		return true;
    	}else {
    		return false;
    	}
    }

}