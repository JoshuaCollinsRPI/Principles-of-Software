package hw5;

import java.util.*;
import java.io.*;

public class ProfessorParser {

	/**
	 * @param: filename     The path to a "CSV" file that contains the
	 *                      "professor","course" pairs
	 * @param: profsTeaching The Map that stores parsed <course,
	 *                      Set-of-professors-that-taught> pairs; usually an empty Map.
	 * @param: profs        The Set that stores parsed professors; usually an empty
	 *                      Set.
	 * @requires: filename != null && profsTeaching != null && profs != null
	 * @modifies: profsTeaching, profs
	 * @effects: adds parsed <course, Set-of-professors-that-taught> pairs to Map
	 *           profsTeaching; adds parsed professors to Set profs.
	 * @throws: IOException if file cannot be read or file not a CSV file following
	 *                      the proper format.
	 * @returns: None
	 */
	public static void readData(String filename, Map<String, Set<String>> profsTeaching, Set<String> profs)
			throws IOException {

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				int i = line.indexOf("\",\"");
				if ((i == -1) || (line.charAt(0) != '\"') || (line.charAt(line.length() - 1) != '\"')) {
					throw new IOException("File " + filename + " not a CSV (\"PROFESSOR\",\"COURSE\") file.");
				}
				String professor = line.substring(1, i);
				String course = line.substring(i + 3, line.length() - 1);

				// Adds the professor to the professor set. If professor is already in, add has
				// no effect.
				profs.add(professor);

				// Adds the professor to the set for the given course
				Set<String> s = profsTeaching.get(course);
				if (s == null) {
					s = new HashSet<String>();
					profsTeaching.put(course, s);
				}
				s.add(professor);
			}
			
			
		}
	}

/*	public static void main(String[] arg) {

		String file = arg[0];

		try {
			Map<String, Set<String>> profsTeaching = new HashMap<String, Set<String>>();
			Set<String> profs = new HashSet<String>();
			readData(file, profsTeaching, profs);
			System.out.println(
					"Read " + profs.size() + " profs who have taught " + profsTeaching.keySet().size() + " courses.");
			
			//System.out.println(profsTeaching.get("CSCI-1200").toString());
			for (String c: profsTeaching.keySet()) {
				System.out.println(c + "\n" + profsTeaching.get(c));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
