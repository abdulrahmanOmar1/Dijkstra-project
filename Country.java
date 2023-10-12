package application;

import java.util.LinkedList;

import javafx.scene.control.Button;

public class Country implements Comparable<Country> {
	float x;
	float y;
	String name;
	LinkedList<Edge> edges = new LinkedList<>();
	private double max = Double.MAX_VALUE;
	private Country tempPrev;
	Country prevousCountry;
	Button test = new Button();
	public Country(String name, float x, float y) { //Country name
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public Country() {

	}

	public void addAdjacentCity(Country country, double distance) {
		edges.add(new Edge(country, distance));
	}

	public void resetTemps() {
		max = Double.MAX_VALUE;
		tempPrev = null;
	}
	
	

	public Button getTest() {
		return test;
	}

	public void setTest(Button test) {
		this.test = test;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public LinkedList<Edge> getAdjacentsList() {
		return edges;
	}

	public void setTempCost(double tempCost) {
		this.max = tempCost;
	}

	public double getTempCost() {
		return max;
	}

	public void setTempPrev(Country tempPrev) {
		this.tempPrev = tempPrev;
	}

	public String getFullName() {
		return name;
	}

	public Country getTempPrev() {
		return tempPrev;
	}

	@Override
	public String toString() {
		return "Country [x=" + x + ", y=" + y + ", name=" + name + "]";
	}

	@Override
	public int compareTo(Country o) {
		if (this.max > o.max)
			return 1;
		else if (this.max < o.max)
			return -1;
		return 0;
	}

}
