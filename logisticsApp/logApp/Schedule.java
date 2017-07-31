package logApp;

import java.util.HashMap;
import java.util.InputMismatchException;

public class Schedule {

	private HashMap<Integer, Integer> facilitySchedule; //<dayNumber, itemsLeft>
	private int facilityRate;
	private int highestDay;
	private int currentDay; //want currentDay to always point to the earliest day thats open
	
	public Schedule(int rateIn){
		this.facilitySchedule = new HashMap<Integer, Integer>();
		currentDay = 1;
		setFacilityRate(rateIn);
		facilitySchedule.put(currentDay, facilityRate);
	}
	
	public int getCurrentDay(){
		return currentDay;
	}
	
	public int getHighestDay(){
		return highestDay;
	}
	
	
	public void setCurrentDay(int day){//to change currentDay
		currentDay = day;
		if (currentDay > highestDay){
			highestDay = currentDay;
		}
	}
	
	public Integer getItemsLeft(Integer intIn){
		return facilitySchedule.get(intIn);
	}
	
	public void setFacilityRate(int rate) throws InputMismatchException {
		
		if (rate < 0) {
			throw new InputMismatchException("Facility Rate must be positive or zero");
		}
		else {
		this.facilityRate = rate;
		}
	}
	
	private void addDay(int day) { //add another HashMap entry and increment currentDay
		if(facilitySchedule.containsKey(day)){//if there is already an entry for a given day don't mess with it
		}
		else //if there isn't add a day there
		facilitySchedule.put(day, facilityRate); 
		
		
	}
	
	public void assignItems(Integer day, Integer numItems) throws InputMismatchException { //set items to be processed to days
		if ((day <= 0)||(numItems<0)){
			throw new InputMismatchException("You must enter a positive/nonzero day number and positive item quantity \n");
		}
		
		if(day > currentDay){
			currentDay = day; //if input day is later than currentDay initialize so it starts on that day
			addDay(currentDay); //make sure there is an entry for days there
		}
		else{
			//if (day < currentDay) then  do nothing and just use the currentDay
		}
		
		while(numItems>0){ //while there are items to be processed
			if (facilitySchedule.get(currentDay)>0){ //if there is time left in the day
				int itemsLeft = facilitySchedule.get(currentDay); //get the number of items left for the day
				facilitySchedule.put(currentDay, (itemsLeft-1)); //decrease number of items left for the day by one
				numItems = numItems-1; //decrease the number of items to be processed
			}
			else{ //there is no more time in that day to process
				addDay(currentDay+1); //add the next day (does nothing if that day already exists and that day will get iterated through if it is also 0)
				setCurrentDay(currentDay+1);//move to the next day
			}
			
			if (facilitySchedule.get(currentDay)==0){ //add this so that you never stop with currentDay on 0
				addDay(currentDay+1); 
				setCurrentDay(currentDay+1);
			}
		}
		//at this point currentDay may not point to the earliest open day (if the input day was higher than current day initially)
		//So iterate through the schedule and look for the first non-zero proc day to set to currentDay
		for(int d = 1; d < currentDay; d++){
			if (facilitySchedule.get(d)!=0){
				currentDay = d;
			}
		}
		
	}
		
		public int returnProcEndDay(Integer day, Integer numItems) throws InputMismatchException { //set items to be processed to days
			if ((day <= 0)||(numItems<0)){
				throw new InputMismatchException("You must enter a positive/nonzero day number and positive item quantity \n");
			}
			int finDay;
			if(day > currentDay){
				finDay = day; //if input day is later than currentDay initialize so it starts on that day
				addDay(finDay); //make sure there is an entry for days there
			}
			else{
				finDay = currentDay; //if day < currentDay initialize so it starts on first open day
			}
			
			int itemsLeft = facilitySchedule.get(finDay); //get local variable for number of items left for the first day
			while(numItems>0){
				if (itemsLeft>0){
					itemsLeft = itemsLeft-1; //dec itemsLeft
					numItems = numItems-1; //dec numItems to process
				}
				else{
					addDay(finDay+1); //add a day otherwise there will be a null pointer exception
					finDay=finDay+1; //inc finDay which does not effect currentDay
					itemsLeft = facilityRate; //change number of items left to the rate so we can iterate through the day
				}
				
			}
			
			return finDay;
		
	}
		
		public int getProcStartDay(Integer day, Integer numItems) throws InputMismatchException { //set items to be processed to days
			int startDay = 0;
			if ((day <= 0)||(numItems<0)){
				throw new InputMismatchException("You must enter a positive/nonzero day number and positive item quantity \n");
			}
			
			int finDay;
			if(day > currentDay){
				finDay = day; //if input day is later than currentDay initialize so it starts on that day
				addDay(finDay); //make sure there is an entry for days there
			}
			else{
				finDay = currentDay; //if day < currentDay initialize so it starts on first open day
			}
			
			int itemsLeft = facilitySchedule.get(finDay); //get local variable for number of items left for the first day
			while(numItems>0){
				if (itemsLeft>0){ //!!this is when the the item first starts processing
					startDay = finDay;
					break;//break in order to exit the loop so that startDay isnt changed
				}
				else{
					addDay(finDay+1); //add a day otherwise there will be a null pointer exception
					finDay=finDay+1; //inc finDay which does not effect currentDay
					itemsLeft = facilityRate; //change number of items left to the rate so we can iterate through the day
				}
				
			}
			
			return startDay;
		}
	
	
}
