package application;

/**
 * Stores time, location, and state information for a person.
 */
public class Person {

	// Steps waited in total since creation of person.
	private int totalWaitTime;
	// Steps waited in bus.
	private int timeInBus;
	// Steps waited at stop.
	private int timeAtStop;
	private boolean inBus;
	
	// Current location of person, whether they are in bus or not.
	private Intersection location;
	
	public Person(Intersection location) {
		this.location = location;
		totalWaitTime = 0;
		timeInBus = 0;
		timeAtStop = 0;
		inBus = false;
	}
	
	/**
	 * Changes location of a Person.
	 * 
	 * @param newLocation New Intersection.
	 * @return true if successful.
	 */
	public boolean move(Intersection newLocation) {
		return false;
	}
	
	/**
	 * Increments step counter instance variables depending on person's state.
	 */
	public void incrementTime() {
		
	}
	
	
}
