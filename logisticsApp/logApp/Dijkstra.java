package logApp;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

//Code taken from http://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map and worked to fit the problem

public class Dijkstra {
	public void computePaths(ArrayList<Vertex> list, Vertex source) {
		int indexStart = list.indexOf(source);

		list.get(indexStart).minDistance = 0.0;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(list.get(indexStart));

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// Visit each edge exiting u
			for (Edge e : u.adjacencies) {

				Vertex v = list.get(list.indexOf(e.target));
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;

				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(u);

					v.minDistance = distanceThroughU;

					int index2 = list.indexOf(u);
					v.previous = list.get(index2);

					int index1 = list.indexOf(v);

					vertexQueue.add(list.get(index1));
				}
			}
		}
	}

	public List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);

		Collections.reverse(path);
		return path;
	}

	/*
	 * public static void main(String[] args) { // mark all the vertices Vertex
	 * A = new Vertex("A"); Vertex B = new Vertex("B"); Vertex D = new
	 * Vertex("D"); Vertex F = new Vertex("F"); Vertex K = new Vertex("K");
	 * Vertex J = new Vertex("J"); Vertex M = new Vertex("M"); Vertex O = new
	 * Vertex("O"); Vertex P = new Vertex("P"); Vertex R = new Vertex("R");
	 * Vertex Z = new Vertex("Z");
	 * 
	 * // set the edges and weight A.adjacencies = new Edge[]{ new Edge(M, 8) };
	 * B.adjacencies = new Edge[]{ new Edge(D, 11) }; D.adjacencies = new
	 * Edge[]{ new Edge(B, 11) }; F.adjacencies = new Edge[]{ new Edge(K, 23) };
	 * K.adjacencies = new Edge[]{ new Edge(O, 40) }; J.adjacencies = new
	 * Edge[]{ new Edge(K, 25) }; M.adjacencies = new Edge[]{ new Edge(R, 8) };
	 * O.adjacencies = new Edge[]{ new Edge(K, 40) }; P.adjacencies = new
	 * Edge[]{ new Edge(Z, 18) }; R.adjacencies = new Edge[]{ new Edge(P, 15) };
	 * Z.adjacencies = new Edge[]{ new Edge(P, 18) };
	 * 
	 * 
	 * computePaths(A); // run Dijkstra System.out.println("Distance to " + Z +
	 * ": " + Z.minDistance); List<Vertex> path = getShortestPathTo(Z);
	 * System.out.println("Path: " + path); }
	 */
}