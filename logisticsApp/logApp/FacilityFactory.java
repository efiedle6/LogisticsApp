package logApp;

import java.util.HashMap;
import java.util.InputMismatchException;

public class FacilityFactory {
	public static Facility build(String facilityType, String facilityName, int facilityRate,
			Stock stocksMap, HashMap<String, Double> linksMap)throws InputMismatchException {
		if (facilityType instanceof String) { // Check that we are receiving
											// Strings as Inputs
			if (facilityType.equals("FacilityStandard")) {
				return new FacilityStandardImpl(facilityName, facilityRate, stocksMap,linksMap);
			} else
				return null;
		} else
			throw new InputMismatchException(
					"Expecting type String but receiving type: "
							+ facilityType.getClass());

	}
}
