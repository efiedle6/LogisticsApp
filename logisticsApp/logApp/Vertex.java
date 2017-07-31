package logApp;

import java.util.ArrayList;

//Code taken from http://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map

public class Vertex implements Comparable<Vertex> {
	public String name;
	public ArrayList<Edge> adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;

	public Vertex(String argName) {
		name = argName;
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.toString().equals(obj.toString())) {
			return true;
		} else
			return false;
	}

}
