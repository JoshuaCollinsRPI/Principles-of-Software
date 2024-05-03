package hw7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import hw4.Graph;

public class CampusParsers {
	/**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "Building","id", "X coordinate", "Y coordinate"
	 *                      rows
	 * @param: id, vals, and graph must exist and aren't null, but new objects
	 * @requires: filename != null 
	 * @effects: adds parsed data to the id hash map, the hashmap holding 
	 * 						building/intersection coordinates, and the graph of campus
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
	public static void parseFileNodes(String filename1, HashMap<String, String> id, 
    		HashMap<String, Double[]> vals, Graph<Double> graph) {
        // Use the HashMaps passed as arguments instead of reinitializing them
        try (BufferedReader reader = new BufferedReader(new FileReader(filename1))) {
            String line;
            // Read each line in the file
            while ((line = reader.readLine()) != null) {
                // Remove quotes and split the line by commas
                String[] fields = line.replace("\"", "").split(",");
                
                // data extracted from parsing the file
                String name = fields[0];
                String rawId = fields[1];
                Double xCoordinate = Double.parseDouble(fields[2]);
                Double yCoordinate = Double.parseDouble(fields[3]);
                
                String temp = "";
                if(fields[0] == "") {
                	temp = "Intersection " + rawId;
                	id.put(rawId, temp);
                }else {
                	id.put(rawId, name);
                }

                Double[] coordinates = {xCoordinate, yCoordinate};
                vals.put(rawId, coordinates);
                graph.addNode(rawId);
                
                // PARSES THIS CORRECTLY
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * @param: Two double arrays of size 2, A and B
	 * @requires: Both A and B have sizes of 2, and hold no null Doubles
	 * @effects: none
	 * @throws: none
	 * @returns: distance between these two points as a Double
	 */
    public static Double measureDist(Double[] A, Double[] B){
        
        Double x1 = A[0];
        Double y1 = A[1];
        
        Double x2 = B[0];
        Double y2 = B[1];
        
        Double DeltaX = Math.abs( x2 - x1 );
        DeltaX = DeltaX * DeltaX;
        Double DeltaY = Math.abs( y2 - y1 );
        DeltaY = DeltaY * DeltaY;
        
        return Math.sqrt( DeltaX + DeltaY );
    }
    
	/**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "Building id", "Other building id "
	 *                      rows
	 * @param: id, vals, and graph must exist and aren't null, but new objects
	 * @requires: filename != null 
	 * @effects: adds parsed data to the campus graph in the form of edges, which have
	 * 						weights calculated by the measureDist method
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
    public static void parseFileEdges(String filename, HashMap<String, Double[]> vals,
            HashMap<String, String> id, Graph<Double> graph) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
    		String line;
    		// Read each line in the file
    		while ((line = reader.readLine()) != null) {
    			// Remove quotes and split the line by commas
    			String[] fields = line.replace("\"", "").split(",");

    			// Extract IDs from the line
    			String id1 = fields[0];
    			String id2 = fields[1];

    			// Check if both IDs are known in the id map
    			if (!id.containsKey(id1) || !id.containsKey(id2)) continue;
    			
    			// Get the coordinates for both IDs from vals
    			Double[] coords1 = vals.get(id1);
    			Double[] coords2 = vals.get(id2);

                Double distance = measureDist(coords1,coords2);
                
    			graph.addEdge(id1, id2, distance);
    			graph.addEdge(id2, id1, distance);
    		}
    	} catch (FileNotFoundException e) {
    		System.err.println("File not found: " + filename);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
