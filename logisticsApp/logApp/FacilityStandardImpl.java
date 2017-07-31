package logApp;

import java.util.HashMap;
import java.util.InputMismatchException;

public class FacilityStandardImpl implements Facility {

	private String name;
	private Schedule schedule;
	private int rate;
	private Stock stock; //HashMap<Item, Integer> 
	private HashMap<String, Double> links;

	public FacilityStandardImpl(String nameIn, int rateIn,Stock stockIn, HashMap<String, Double> linksIn)
			throws NullPointerException, InputMismatchException {
		setName(nameIn);
		setSchedule(rateIn);
		setRate(rateIn);
		setStock(stockIn);
		setLinks(linksIn);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) throws NullPointerException {
		if (name == null) {
			throw new NullPointerException("Null value Passed to setName");
		}
		this.name = name;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(int rateIn) throws InputMismatchException {
	 
		if (rateIn < 0) {
			throw new InputMismatchException("Facility Rate must be positive or zero");
		}
		else {
		this.schedule = new Schedule(rateIn);
		}
	}

	public int getRate() {
		return rate;
	}

	private void setRate(int rate) throws InputMismatchException {
		if (rate <= 0) {
			throw new InputMismatchException("Negative value Passed to setRate");
		}
		this.rate = rate;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock)
			throws NullPointerException {
		if (stock == null) {
			throw new NullPointerException("Null value Passed to setStock");
		}
		this.stock = stock;
	}

	public HashMap<String, Double> getLinks() {
		return links;
	}

	private void setLinks(HashMap<String, Double> links)
			throws NullPointerException {
		if (links == null) {
			throw new NullPointerException("Null value Passed to setLinks");
		}
		this.links = links;
	}
	

}
