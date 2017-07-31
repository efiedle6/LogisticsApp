package logApp;

import java.util.ArrayList;
import java.util.InputMismatchException;

//This is the only Network loader we will need, no need for factories or Impls because we will always load the network from the Facility List as
//it has already been loaded with the links. This reduces redundancy and sources of error from loading links from multiple sources or multiple times
//from the same source.

public class NetworkLoader {

	public static ArrayList<Vertex> loadNetwork(FacilityService facilityServ)
			throws InputMismatchException {
		if (facilityServ instanceof FacilityService) { // check to make sure we are being passed a FacilityService
			ArrayList<Vertex> vertexList = new ArrayList<Vertex>(facilityServ
					.getFacilityList().size());

			for (int i = 0; i < facilityServ.getFacilityList().size(); i++) { // initialize the ArrayList

				String linkStart = facilityServ.getFacilityList().get(i)
						.getName(); // should be the facility name
				Vertex tempStart = new Vertex(linkStart);
				vertexList.add(i, tempStart);
				vertexList.get(i).adjacencies = new ArrayList<Edge>();

			}

			for (int i = 0; i < facilityServ.getFacilityList().size(); i++) {
				for (String name : facilityServ.getFacilityList().get(i)
						.getLinks().keySet()) {

					// System.out.println(facilityServ.getFacilityList().get(i).getLinks().keySet());

					String linkStart = facilityServ.getFacilityList().get(i)
							.getName(); // should be the facility name
					String linkDestination = name.toString(); // should be the link name for this iteration

					Vertex v1 = new Vertex(linkStart);
					Vertex v2 = new Vertex(linkDestination);

					Double linkDistance = facilityServ.getFacilityList().get(i)
							.getLinks().get(name); // should be the distance for the link in this iter.

					int index = vertexList.indexOf(v1);
					int index2 = vertexList.indexOf(v2);

					Edge e = new Edge(vertexList.get(index2), linkDistance);
					vertexList.get(index).adjacencies.add(e);

				}
			}

			/*
			 * //for testing for (int q = 0; q < vertexList.size(); q++) {
			 * String vertex = vertexList.get(q).toString(); for (int r = 0; r <
			 * vertexList.get(q).adjacencies.size(); r++) { String edge =
			 * vertexList.get(q).adjacencies.get(r).toString();
			 * System.out.println("Vertex: " + vertex + edge); }
			 * 
			 * }
			 */

			return vertexList;
		} else
			throw new InputMismatchException(
					"Expecting type FacilityService but receiving type: "
							+ facilityServ.getClass());

	}
}
