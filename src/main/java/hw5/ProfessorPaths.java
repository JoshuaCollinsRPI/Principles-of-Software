package hw5;

import hw4.Graph;
import java.util.*;
import java.io.*;

public class ProfessorPaths{
	
	public Graph<String> graph;
	
	/*
	 * Base Constructor
	 * @modifies: initializes graph object
	 * @effects: initializes graph object
	 * */
	public ProfessorPaths() {
		graph = new Graph<String>(); 
	}
	
	/*
	 * creates a full graph
	 * @parameter: fileName
	 * @requires: fileName leads to an valid file
	 * @modifies: initializes a new graph object and adds data to graph
	 * @effects: initializes a new graph object and adds data to graph
	 * */
	public void createNewGraph(String filename) {
	    Map<String, Set<String>> ProfsInClass = new HashMap<>();
	    Set<String> Profs = new HashSet<>();
	    graph = new Graph<String>();
	    try {
	        ProfessorParser.readData(filename, ProfsInClass, Profs);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // create blank nodes for all professors mentioned in the csv file
	    for (String c: Profs) {
	        graph.addNode(c);
	    }

	    // all the connections between these professor nodes
	    for (String Course: ProfsInClass.keySet()) {
	        for (String Prof1: ProfsInClass.get(Course)) {
	            for (String Prof2: ProfsInClass.get(Course)) {
	                graph.addEdge(Prof1, Prof2, Course);
	            }
	        }
	    }
	}
	
	
	/*
	 * finds a path via BFS
	 * @parameter: start and destination
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @returns: a string that displays the optimal path to destination from start as a list
	 * */
	public String findPath(String Start, String Destination) {
	    String statement = new String();
	    if (!(graph.contains(Start)) || !(graph.contains(Destination))) {
	        if (!(graph.contains(Start))) {
	            statement += "unknown professor " + Start + "\n";
	        }
	        if (!(graph.contains(Destination)) && !(Start.equals(Destination))) {
	            statement += "unknown professor " + Destination + "\n";
	        }
	        return statement;
	    }
	    
	    ArrayList<String> final_path = new ArrayList<String>();
	    
	    

	    final_path = graph.BreadthFirstSearch(Start, Destination); 
	    //System.out.println(final_path);
	    statement +=  "path from " + Start + " to " + Destination + ":\n";
	    if (final_path == null) {
	        statement += "no path found\n";
	        return statement;
	    } else if (final_path.size() >= 1) {
	        statement += Start + " to " + final_path.getFirst() + " via " + graph.getWeight(Start, final_path.getFirst()) + "\n";
	        for (int x = 0; x < final_path.size() - 1; x++) {
	            String from = final_path.get(x);
	            String to = final_path.get(x + 1);
	            statement += from + " to " + to + " via " + graph.getWeight(from, to) + "\n";
	        }
	    } 
	    return statement;
	}


	/*
	 * returns boolean to see if x is in graph
	 * @parameter: x
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @returns: true if x is in graph and false otherwise
	 * */
	public Boolean has(String x) {
		return graph.contains(x);
	}
	
	/*
	 * Return bool for the connection b/w a and b called z
	 * @parameters: a,b,z
	 * @requires: a and b are in the graph and all inputs are strings
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: true if b is connected to a and the edge is called z and false otherwise
	 * */
	public Boolean connection(String a, String b) {
		if (graph.contains(a)&&graph.contains(b)) {
			ArrayList<String> kids = new ArrayList<String>();
			kids = graph.findChildren(a);
			return kids.contains(b);
		}else {
			return false;
		}
	}
	
	/*
	 * Return size of graph
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: number of nodes
	 * */
	public int size() {
		return graph.size();
	}
	
	/*
	 * Return size of edges
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: number of edges
	 * */
	public int edgeSize() {
		return graph.edgeSize();
	}
	
	
//	public static void main(String[] args) {
//		String file = "data/test1.csv";
//
//		ProfessorPaths test = new ProfessorPaths();
//		test.createNewGraph(file);
//
//		//System.out.println("There are " + x1 + " many nodes, which each represent a professor.");
//		System.out.println(test.edgeSize());
//		
//		System.out.println(test.findPath("Konstantin Kuzmin", "Steven Walker"));
//		
//		System.out.println(test.findPath("Konstantin Kuzmin", "John Doe"));
//		
//		System.out.println(test.findPath("Konstantin Kuzmin", "Konstantin Kuzmin"));
//		
//		
//		System.out.println(test.findPath("Invalid Name A", "Konstantin Kuzmin"));
//		System.out.println(test.findPath("Konstantin Kuzmin", "Invalid Name B"));
//		System.out.println(test.findPath("Invalid Name A", "Invalid Name B"));
//		//System.out.println(profsTeaching.get("CSCI-1200").toString());
//	}
//	
}