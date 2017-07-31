package logApp;

import java.util.HashMap;

public interface Facility {
	public String getName();

	public HashMap<String, Double> getLinks();

	public Stock getStock();

	public Schedule getSchedule();
	
	public int getRate();
}
