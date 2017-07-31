package logApp;

import java.util.ArrayList;

public interface FacilityLoader { // Interface for any facilityLoaderImpls you may create, In our case there is only 1

	public default ArrayList<Facility> loadFacilities(String fileName, String facilityType,
			ItemService itemServ) {
		return null;

	}

}
