package application;

/**
 * Stores time, location, and state information for a person.
 */
public class Person {

	// Steps waited in bus.
	private int timeInBus;
	// Steps waited at stop.
	private int timeAtStop;
	private boolean inBus;

	// Current location of person, whether they are in bus or not.
	private Intersection location;
	// Goal location of a person. Does not change.
	private final Intersection DESTINATION;

	public Person(Intersection location, Intersection destination) {
		this.location = location;
		this.DESTINATION = destination;
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
		// TODO: Check how many people are already there. If there are more than can be
		// displayed, do not allow a person to move there? Or, make a way to display
		// that there are more.
		
		location = newLocation;

		return true;
	}

	/**
	 * Increments step counter instance variables depending on person's state.
	 */
	public void incrementTime() {
		if (inBus) {
			timeInBus++;
		} else {
			timeAtStop++;
		}
	}

	/**
	 * @return the location
	 */
	public Intersection getLocation() {
		return location;
	}
	
	public Intersection getDestination() {
		return DESTINATION;
	}

	/**
	 * @return the inBus
	 */
	public boolean isInBus() {
		return inBus;
	}

	/**
	 * @param inBus the inBus to set
	 */
	public void setInBus(boolean inBus) {
		this.inBus = inBus;
	}

	/**
	 * Calculated the total wait time by adding the time waiting inside and outside a bus.
	 * 
	 * @return the total wait time
	 */
	public int getTotalWaitTime() {
		return timeInBus + timeAtStop;
	}

	/**
	 * @return the timeInBus
	 */
	public int getTimeInBus() {
		return timeInBus;
	}

	/**
	 * @return the timeAtStop
	 */
	public int getTimeAtStop() {
		return timeAtStop;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Intersection location) {
		this.location = location;
	}

}
