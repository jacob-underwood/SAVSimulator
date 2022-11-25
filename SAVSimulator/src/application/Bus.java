package application;

import java.util.PriorityQueue;

public abstract class Bus {

	// Current Intersection.
	private Intersection location;
	// Current goal Intersection.
	private Intersection destination;
	// Maximum number of people allowed in bus.
	private int capacity;
	// Child classes create list for storing passengers.
	
	public Bus(Intersection location, int capacity) {
		
	}
	
	/**
	 * Moves bus closer to destination.
	 */
	public void move() {
		
	}
	
	/**
	 * Picks up as many people as possible at location.
	 */
	public void pickup() {
		
	}
	
	/**
	 * Determines destination based on algorithm implemented in child class.
	 */
	public abstract void setDestination();
	
	
	
}
