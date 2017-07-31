package logApp;

//Code taken from http://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map

public class Edge {
	public Vertex target;
	public double weight;

	public String toString() {
		String retString = // (" Edge: " + target.toString() + " Weight: " +
							// weight);
		target.toString();
		return retString;
	}

	public Edge(Vertex argTarget, double argWeight) {
		target = argTarget;
		weight = argWeight;
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
