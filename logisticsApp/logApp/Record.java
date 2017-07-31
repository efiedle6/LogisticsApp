package logApp;

import java.util.InputMismatchException;

public class Record {

	private Facility site;
	private int itemCount; //this is the number of items a Record takes for a solution
	private int itemsGiven; //This is the number of items given towards a solution, 0 when created
	private int pStartDay;
	private int pEndDay;
	private Double percentAtStart; //percent of day used for first day
	private Double percentAtEnd; //percent of day used for last day
	private Double travelTime;
	private int arrivalDay;
	
	
	public Record(Facility siteIn, int itemCountIn, int pStartDayIn, int pEndDayIn, Double travelTimeIn) throws NullPointerException, InputMismatchException{
		//error checking in set methods
		setSite(siteIn);
		setItemCount(itemCountIn);
		setPStartDay(pStartDayIn);
		setPEndDay(pEndDayIn);
		setTravelTime(travelTimeIn);
		setArrivalDay(pEndDayIn, travelTimeIn);
		this.itemsGiven = 0;
		setPercentAtStart(pStartDayIn);
	}

	

	public String toString(){
		return (site.getName() + ": " + arrivalDay +", ");
	}

	public Facility getSite() {
		return site;
	}

	public int getItemsGiven(){
		return itemsGiven;
	}
	
	public void setItemsGiven(int numItems){
		this.itemsGiven = numItems;
	}
	
	public Double getPercentAtStart(){
		return percentAtStart;
	}
	
	private void setPercentAtStart(Integer pStartDayIn) {
		double itemsLeft = site.getSchedule().getItemsLeft(pStartDayIn);
		percentAtStart = ( itemsLeft / site.getRate());
		
	}
	
	public Double getPercentAtEnd(){
		return percentAtEnd;
	}
	
	public void setPercentAtEnd() {
		double itemsLeft = site.getSchedule().getItemsLeft(pEndDay);
		percentAtEnd = ( (site.getRate() - itemsLeft) / site.getRate()); //take the rate minus the items left to find out how many were used for this item
		
	}
	
	public void setSite(Facility site) throws NullPointerException{
		if(site==null){
			throw new NullPointerException();
		}
		else
		this.site = site;
	}


	public int getItemCount() {
		return itemCount;
	}


	public void setItemCount(int itemCount) throws InputMismatchException {
		if(itemCount<0){
			throw new InputMismatchException("itemCount must be zero or positive");
		}
		else
		this.itemCount = itemCount;
	}


	public int getPEndDay() {
		return pEndDay;
	}


	public void setPEndDay(int pEndDay) throws InputMismatchException{
		if(pEndDay<0){
			throw new InputMismatchException("pEndDay must be positive");
		}
		else
		this.pEndDay = pEndDay;
	}
	
	public int getPStartDay() {
		return pStartDay;
	}


	public void setPStartDay(int pStartDay) throws InputMismatchException{
		if(pStartDay<0){
			throw new InputMismatchException("pStartDay must be positive");
		}
		else
		this.pStartDay = pStartDay;
	}


	public Double getTravelTime() {
		return travelTime;
	}


	public void setTravelTime(Double travelTime) throws NullPointerException, InputMismatchException{
		if(travelTime==null){
			throw new NullPointerException();
		}
		else if(travelTime<0.0){
			throw new InputMismatchException("Travel time must be positive");
		}
		else
		this.travelTime = travelTime;
	}


	public int getArrivalDay() {
		return arrivalDay;
	}


	public void setArrivalDay(int pEndDayIn, Double travelTimeIn) throws NullPointerException, InputMismatchException{
		if(travelTime==null){
			throw new NullPointerException();
		}
		else if((travelTime<0.0)||(pEndDayIn < 0)){
			throw new InputMismatchException("Travel time and pEndDay must be positive");
		}
		else{
		Double arrivalDay = travelTimeIn+(pEndDayIn + 1); //travel starts on the day after processing ends
		int retDay = (int) Math.ceil(arrivalDay); //Round up 
		this.arrivalDay = retDay;
		}
	}
	
}
