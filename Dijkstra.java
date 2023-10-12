package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
	static ArrayList<Country> Countrys = new ArrayList<Country>();
	static HashMap<String, Country> allNodes = new HashMap<>();
	private Country source;
	private Country destination;
	private PriorityQueue<Country> heap;

	public Dijkstra() {
	}

	public Dijkstra(ArrayList<Country> Countrys, Country source, Country destination) {
		heap = new PriorityQueue<>(Countrys.size());
		this.destination = destination;
		this.Countrys = Countrys;
		for (Country country : Countrys) {
			country.resetTemps();
			if (country == source) {
				country.setTempCost(0);
			}
			heap.add(country);
		}
	}

	public void generateDijkstra() {
		while (!heap.isEmpty()) {
			Country minUnknown = heap.poll(); 
			LinkedList<Edge> adjacentsList = minUnknown.getAdjacentsList(); 
			for (Edge edge : adjacentsList) { 
				Country adjacentCity = edge.getAdjacentCity();
					if ((minUnknown.getTempCost() + edge.getDistance()) < adjacentCity.getTempCost()) {
						heap.remove(adjacentCity);
						adjacentCity.setTempCost(minUnknown.getTempCost() + edge.getDistance());
						adjacentCity.setTempPrev(minUnknown);
						heap.add(adjacentCity);
					}
				
			}
		}
	}

	private String pathString;
	 String distanceString;

	public Country[] pathTo(Country destination) {
		LinkedList<Country> countries = new LinkedList<>();
		Country iterator = destination;
		distanceString = String.format("%.2f", destination.getTempCost());
		while (iterator != source) {
			countries.addFirst(iterator);
			pathString = iterator.getFullName() + " : " + String.format("%.2f", iterator.getTempCost()) + "  KM" + "\n"
					+ "\t\t,  " + pathString;
			iterator = iterator.getTempPrev();
		}

		return countries.toArray(new Country[0]);
	}

	public String getPathString() {
		if (countOccurrences(pathString, '\n') <= 1) {
			pathString = "No Path";
			distanceString = "\t\t\t------------------";
			return pathString;
		}

		pathString = "\t" + pathString;

		int truncateIndex = pathString.lastIndexOf('\n');
		pathString = pathString.substring(0, truncateIndex);

		return pathString;
	}

	private static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	public static ArrayList<Country> readFile() throws FileNotFoundException {
		String line = null;
		int numberOfCountryes, numberOfEdges;
		File file = new File("data.txt");
		Scanner scan = new Scanner(file);
		line = scan.nextLine();
		String[] str = line.split(" ");
		numberOfCountryes = Integer.parseInt(str[0]);
		numberOfEdges = Integer.parseInt(str[1]);
		
		for (int i = 0; i < numberOfCountryes; i++) {
			float x, y;
			line = scan.nextLine();
			String[] strN = line.split(" ");
			x = (float) Double.parseDouble(strN[1]);
			y = (float) Double.parseDouble(strN[2]);
			Country newCountry = new Country(strN[0], x, y);
			Countrys.add(newCountry);
			allNodes.putIfAbsent(strN[0], newCountry);
			
		}
		for (int i = 0; i < numberOfEdges; i++) {
			line = scan.nextLine();
			String[] strN = line.split(" ");
			String fromCityName = strN[0], toCityName = strN[1];
			Country fromCity = allNodes.get(fromCityName), toCity = allNodes.get(toCityName);
			double distance = distance(fromCity.x,fromCity.y,toCity.x,toCity.y);
			fromCity.addAdjacentCity(toCity, distance); 
		}

		return Countrys;

	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		 return 6378.8 * Math.acos((Math.sin(Math.toRadians(y1)) * Math.sin(Math.toRadians(y2)))
							+ Math.cos(Math.toRadians(y1)) * Math.cos(Math.toRadians(y2))
									* Math.cos(Math.toRadians(x1) - Math.toRadians(x2)));
		  }


}
