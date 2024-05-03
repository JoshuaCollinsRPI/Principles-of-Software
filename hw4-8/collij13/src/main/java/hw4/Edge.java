package hw4;

public class Edge<W>{
	private String name;
	private W val;
	
	// Rep invariant:
	// 		values cannot be null
	// Abstraction:
	// 		represents a line between nodes on a graph
	
	
	/*
	 * Base Constructor
	 * @modifies: initializes string name and W val variables
	 * @effects: initializes edge object
	 * */
	public Edge(String Name, W value){
		this.name = Name; 
		this.val = value;
	}
	
	public Edge(Edge<W> other) {
		this.name = other.name(); 
		this.val = other.weight();
	}
	
	/*
	 * Returns name of an edge
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: name of edge
	 * */
	public String name() {
		return this.name;
	}
	
	/*
	 * Returns weight of an edge
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: weight of an edge
	 * */
	public W weight() {
		return this.val;
	}
	
	@Override
	// prints edge as the desired string
	public String toString() {
		String out = name + "(" + val + ")";
		return out;
	}
}