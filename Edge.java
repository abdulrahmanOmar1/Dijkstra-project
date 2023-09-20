package application;


public class Edge {
	Country adjacent;
	double distance;
	public Edge(Country adjacentCity, double distance) {
		this.adjacent = adjacentCity;
		this.distance = distance;
	}

	public Country getAdjacentCity() {
		return adjacent;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "Edge [adjacent=" + adjacent + ", distance=" + distance + "]";
	}
	
}
