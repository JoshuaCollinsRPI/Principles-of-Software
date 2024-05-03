package hw7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import hw4.*;

public class CampusPaths {
	public HashMap<String, String> id_saved;
	public HashMap<String, Double[]> coordinates;
	public Graph<Double> campus;
	
	/**
	 * @param: Two strings for files
	 * @requires: Both must be actual files
	 * @effects: initalizes static variables
	 * @throws: none
	 * @returns: none
	 */
	public CampusPaths(String f1, String f2) throws IOException {
	    id_saved = new HashMap<>();
	    coordinates = new HashMap<>();
	    campus = new Graph<Double>();

	    CampusParsers.parseFileNodes(f1, id_saved, coordinates, campus);
	    CampusParsers.parseFileEdges(f2, coordinates, id_saved, campus);
	}

	
	/**
	 * @param: Two double arrays of size 2, A and B
	 * @requires: Both A and B have sizes of 2, and hold no null Doubles
	 * @effects: none
	 * @throws: none
	 * @returns: angle of these two points forming a right triangle
	 */
	public static Double degree(Double[] A, Double[] B){
		Double x = B[0] - A[0];
    	Double y = A[1] - B[1];
    	
    	Double out = Math.atan2(y,x)*180/Math.PI;
    	return out;
	}
	/**
	 * @param: Two double arrays of size 2, A and B
	 * @requires: Both A and B have sizes of 2, and hold no null Doubles
	 * @effects: none
	 * @throws: none
	 * @returns: Direction the angle, given by the method degree(), faceses.
	 */
    public static String direction(Double[] A, Double[] B) {

    	Double theta = degree(A,B);

        if (-22.5 <= theta && theta < 22.5) return "East";
        else if (22.5 <= theta && theta < 67.5) {
            return "NorthEast";
        }
        else if (67.5 <= theta && theta < (112.5)) {
            return "North";
        }
        else if (112.5 <= theta && theta < 157.5) {
            return "NorthWest";
        }
        else if (157.5 <= theta || -157.5 >= theta) {
            return "West";
        }
        else if (-157.5 <= theta && theta < -112.5) {
            return "SouthWest";
        }
        else if (-112.5 <= theta && theta < -67.5) {
            return "South";
        }
        return "SouthEast";
    	
    }
    
	/**
	 * @param: HashMap id (Key: id for buildings, Value: name of building) 
	 * 		   and String given
	 * @requires: is is not null and niether is given
	 * @effects: none
	 * @throws: none
	 * @returns: id respective to given if given is a building name, return given 
	 * 			 if given is already an id
	 */
	public static String StrToId(HashMap<String, String> id, String given){
		if (id.containsKey(given)){
			return given;
		}else{
			// given is either a intersection or a building
			for(String x: id.keySet()) {
				if(id.get(x).equals(given)) {
					return x;
				}
			}
		}
		return "Unknown building: ["+given+"]";
	}
	
	/**
	 * @param: graph, id, vals, id_1, and id_2
	 * @requires: all parameters cannot be null, the strings may be "" though
	 * @effects: none
	 * @throws: none
	 * @returns: String that represents the optimal path between two 
	 *           buildings on campus
	 */
	private static String findPath(Graph<Double> graph, HashMap<String, String> id, 
    	HashMap<String, Double[]> vals, String id_1, String id_2) throws Exception{
		
		String out = "";
		
		String id1 = StrToId(id,id_1);
		String id2 = StrToId(id,id_2);
		
		boolean A = id.containsKey(id1);
		boolean B = id.containsKey(id2);
		if(!A && !B) {
			out += (id1);
			if(!id1.equals(id2)){
				out += (id2);
			}
			return out;
		}else if (!A || !B){
			if(!A){
				out += (id1);
				return out;
			}else if(!B){
				out += (id2);
				return out;
			}	
		}else {
			if (id.get(id1).contains("Intersection")) {
				out += "Unknown building: ["+id1+"]";
				if (id.get(id2).contains("Intersection") && !id1.equals(id2)) {
					out += "Unkown building: ["+id2+"]";
				}
				return out;
			}
		}
		Trail temp = graph.dijkstra(id1, id2);
		
		if (id1.equals(id2)) {
			return "Path from "+id.get(id1)+" to "+id.get(id2)+":\n"+"Total distance: 0.000 pixel units.";
		}
		out += "Path from "+id.get(id1)+" to "+id.get(id2)+":\n";
		if(temp == null || temp.trail.size() == 0) {
			return "There is no path from "+id.get(id1)+" to "+id.get(id2)+".\n";
		}
		//out += "\t\tWalk "+this.direction(vals.get(id1),vals.get(id2))+" to ("+id.get(id2)+")\n";
		Edge<Double> previous = new Edge<Double>(id1,0.0);
		for(Edge<Double> edge: temp.trail()) {
			// gives building id and 
			out += "\tWalk "+direction(vals.get(previous.name()),vals.get(edge.name()))
				+  " to ("+id.get(edge.name())+")\n";
			previous = new Edge<Double>(edge);
		}
		out += String.format("Total distance: %.3f pixel units.",temp.price());
		
		return out;
	}
	
	/**
	 * @requires: none
	 * @effects: gives user access to controller method using static variables
	 * @throws: none
	 * @returns: none
	 */
	public void console() {
		try {
			controller(campus, id_saved, coordinates);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void controller(Graph<Double> graph, HashMap<String, String> id, 
	    	HashMap<String, Double[]> vals) {
		try (Scanner command = new Scanner(System.in)) {
			while(true) {
				// stores command input from user
				String request = command.nextLine();
				
				if(request.equals("b")){
					ArrayList<String> out = new ArrayList<String>();
					for(String x: id.keySet()) {
						String name = id.get(x);
						if(name.contains("Intersection")) {
							continue;
						}else {
							out.add(name+","+x);
						}
					}
					Collections.sort(out);
					for(String x: out) {
						System.out.println(x);
					}
				}else if(request.equals("r")) {
					System.out.print("First building id/name, followed by Enter: ");
					String B1 = command.nextLine();
					System.out.print("Second building id/name, followed by Enter: ");
					String B2 = command.nextLine();
					
					try {
						System.out.println(findPath(graph, id, vals, B1, B2));
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}else if(request.equals("q")) {
					break;
				}else if(request.equals("m")) {
					System.out.println("b lists all buildings\n"
							+ "r prints directions for the shortest route between any two buildings\n"
							+ "q quits the program\n"
							+ "m prints a menu of all commands");
				}else {
					System.out.println("Unknown option");
				}		
			}
		}
	}
	
    public static void main(String[] args) throws IOException {
        // Add the filename as a command-line argument
        String file1 = "data/RPI_map_data_Nodes.csv";
        String file2 = "data/RPI_map_data_Edges.csv";

        // Key: id,     value: name
        HashMap<String, String> id = new HashMap<>();
        // stores coordinates by id
        HashMap<String, Double[]> vals = new HashMap<>();
        // holds edge weights and nodes (buildings/intersections)
    	Graph<Double> graph = new Graph<Double>(); 

    	CampusParsers.parseFileNodes(file1, id, vals, graph);
    	CampusParsers.parseFileEdges(file2, vals, id, graph);
        // CONTROLLER BELOW
        controller(graph, id, vals);
    }
}