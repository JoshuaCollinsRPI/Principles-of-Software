package hw4;

import java.util.ArrayList;

public class Trail implements Comparable<Trail> {
    public ArrayList<Edge<Double>> trail;
    public String start;
    public String stops;
    public Double price;

    // Constructor
    public Trail(String from, String to) {
        this.trail = new ArrayList<>();
        this.start = from;
        this.stops = to;
        this.price = 0.0;
    }

    // Add an edge to the trail and return a new trail
    public Trail add(Edge<Double> node) {
        Trail newTrail = new Trail(start, node.name());
        newTrail.trail = new ArrayList<>(this.trail);
        newTrail.trail.add(node);
        newTrail.price = this.price + node.weight();
        return newTrail;
    }

	/*
	 * Returns trail
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: trail
	 * */
    public ArrayList<Edge<Double>> trail() {
        return this.trail;
    }

	/*
	 * Returns price to wander trail
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: price to wander trail
	 * */
    public Double price() {
        return this.price;
    }
    
	/*
	 * Returns last node connected to the trail
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: name of edge
	 * */
    public String end() {
    	if(trail.isEmpty()) {
        	return stops; 
        }else {
        	return trail.get(trail.size() - 1).name();
        }
    }

    // comparable
    @Override
    public int compareTo(Trail other) {
        return Double.compare(this.price, other.price);
    }

    // way to equate and compare similarity of 2 trails
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Trail) {
            Trail otherTrail = (Trail) other;
            return this.start.equals(otherTrail.start) && this.stops.equals(otherTrail.stops) && this.price.equals(otherTrail.price);
        }
        return false;
    }
    
    //  hash code 
    @Override
    public int hashCode() {
        return start.hashCode() + stops.hashCode() + price.hashCode();
    }
}
