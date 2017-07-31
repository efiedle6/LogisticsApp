package logApp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class NetworkService {

	private static ArrayList<Vertex> facilityNetwork;
	private static NetworkService instance;

	private NetworkService(FacilityService facilityServ)
			throws InputMismatchException {
		if (facilityServ instanceof FacilityService) { // Check that we are receiving Strings as Inputs

			// Load the facilities-- In this implementation there is no need to create a factory and load a file
			// The Network will always load the links from the Facility List in order to reduce redundancy
			facilityNetwork = NetworkLoader.loadNetwork(facilityServ);
		} else {
			throw new InputMismatchException(
					"Expecting type FacilityService but receiving types: "
							+ facilityServ.getClass());
		}
	}

	public static NetworkService getInstance(FacilityService facilityServ) { // Singleton Facade Pattern
		if (facilityServ instanceof FacilityService) {// Check that we are receiving Strings as Inputs
			if (instance == null)
				instance = new NetworkService(facilityServ);
			return instance;
		} else {
			throw new InputMismatchException(
					"Expecting type FacilityService but receiving types: "
							+ facilityServ.getClass());
		}
	}

	public ArrayList<Vertex> getFacilityNetwork() {
		return facilityNetwork;
	}

	public Double shortestPath(String facilityStart,String facilityDestination)
			throws InputMismatchException {

		Vertex startVert = new Vertex(facilityStart);
		Vertex destVert = new Vertex(facilityDestination);

		ArrayList<Vertex> copyList = new ArrayList<Vertex>();// Create copy of network so that shortestPath returns the correct distance everytime
		for (int k = 0; k < instance.getFacilityNetwork().size(); k++) {
			Vertex tempVert = new Vertex(instance.getFacilityNetwork().get(k).toString());

			if (!copyList.contains(tempVert)) {
				copyList.add(tempVert);
				tempVert.adjacencies = new ArrayList<Edge>();
			}
			for (Edge name : instance.getFacilityNetwork().get(k).adjacencies) {
				int destIndex = instance.getFacilityNetwork().get(k).adjacencies
						.indexOf(name);
				Edge destName = instance.getFacilityNetwork().get(k).adjacencies
						.get(destIndex);
				Edge e = new Edge(destName.target, destName.weight);
				copyList.get(k).adjacencies.add(e);
			}
		}

		if (copyList.contains(startVert) & copyList.contains(destVert)) { // check that both Strings are actually facilities

			int indexStart = copyList.indexOf(startVert);
			int indexDest = copyList.indexOf(destVert);

			Dijkstra graph = new Dijkstra();
			graph.computePaths(copyList, copyList.get(indexStart)); // run Dijkstra

			List<Vertex> path = graph
					.getShortestPathTo(copyList.get(indexDest));

			Double dist = 0.0;
			for (int i = 0; i < path.size(); i++) {
				if (i == 0) {

				} else {
					int currentIndex = copyList.indexOf(path.get(i));
					Edge v = new Edge(path.get(i - 1), 0.0);
					int workingIndex = copyList.get(currentIndex).adjacencies
							.indexOf(v);
					dist = dist
							+ copyList.get(currentIndex).adjacencies
									.get(workingIndex).weight;
				}
			}
			
			/*System.out.println("Path: " + path);
			System.out.println("Distance to " + facilityDestination + " From "
					+ facilityStart + ": " + dist + " mi");
			// convert miles to days
			Double byDayDistance = (dist / (TRAVEL_TIME * MPH));
			DecimalFormat df = new DecimalFormat("##.##"); // round to 2 decimal
															// places
			Double byDayDistance2 = Double
					.parseDouble(df.format(byDayDistance));
			System.out.print(dist + " mi / (8 hours per day * 50 mph) = "
					+ byDayDistance2 + " days");
			*/
			
			return dist;

		} else
			throw new InputMismatchException( // throw exception if inputs are
												// not actually facilities
					"Start or destination point not present in the facility network ");

	}

}
