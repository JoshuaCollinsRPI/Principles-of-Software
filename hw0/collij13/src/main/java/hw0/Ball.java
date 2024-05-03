/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.awt.Color;

/**
 * This is a simple object that has a volume.
 */
// You may not make Ball implement the Comparable interface.
public class Ball {

    private double volume;   
    private Color color;

    /**
     * Constructor that creates a new ball object with the specified volume and color.
     * @param volume the volume of the new ball object
     * @param color the color of the new ball object
     */
    public Ball(double volume, Color color) {
        this.volume = volume;
        this.color = color;
    }
    
    /**
     * Constructor that creates a new ball object with the specified volume given by a string.
     * @param volume A string representing the volume of the new object.
     */
    public Ball(String volume, Color color) {
		//this(Double.parseDouble(volume), color);
		try {
			// testing to make sure this string can be converted into 
			// a double, if it can, the volume in converted and accepted
			double volume_input = Double.parseDouble(volume);
			this.color = color;
			this.volume = volume_input;
		}catch(Exception x){
			// for the case where the volume inputed is not a valid input
			this.volume = 0;
		}
			
    }    

    /**
     * Returns the volume of the ball.
     * @return the volume of the ball.
    */
    public double getVolume() {
        return this.volume;
    }
    
    /**
     * Returns the color of the ball.
     * @return the color of the ball.
    */
    public Color getColor() {
        return this.color;
    }

}


