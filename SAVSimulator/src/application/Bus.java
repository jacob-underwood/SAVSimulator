package application;

import java.util.AbstractCollection;
import java.util.PriorityQueue;

public abstract class Bus {

	// Current Intersection.
	private Intersection location;
	// Current goal Intersection.
	private Intersection destination;
	// Maximum number of people allowed in bus.
	private int capacity;
	// Child classes instantiates with LinkedList or PriorityQueue for storing passengers.
	private AbstractCollection<Person> passengers;

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

	protected void setDestination() {

	}

	/**
	 * Determines destination based on algorithm implemented in child class. Then
	 * sets the destination instance variable to that.
	 */
	public abstract void generateDestination();

}
