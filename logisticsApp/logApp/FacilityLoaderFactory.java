package logApp;

import java.util.InputMismatchException;

public class FacilityLoaderFactory {

	public static FacilityLoader build(String fileType)
			throws InputMismatchException {
		if (fileType instanceof String) { // Check that we are receiving Strings as Inputs
			if (fileType.equals("XML")) {
				return new FacilityLoaderXML();
			} else
				return null;
		} else
			throw new InputMismatchException(
					"Expecting type String but receiving type: "
							+ fileType.getClass());

	}
}
