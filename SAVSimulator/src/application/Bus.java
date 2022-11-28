package application;

import java.util.AbstractCollection;
import java.util.PriorityQueue;

public abstract class Bus<T extends AbstractCollection<Person>> {

	// Current Intersection.
	private Intersection location;
	// Current goal Intersection.
	private Intersection destination;
	// Maximum number of people allowed in bus.
	private int capacity;
	// Child classes instantiates with LinkedList or PriorityQueue for storing
	// passengers.
	protected T passengers;

	// Access to the city to know the location of people waiting and the grid
	// layout.
	private City city;

	public Bus(Intersection location, int capacity, City city) {
		this.location = location;
		this.capacity = capacity;
		this.city = city;

		this.destination = null;
		this.passengers = null;
	}

	/**
	 * Moves bus closer to destination.
	 * 
	 * @throws IllegalStateException if bus is already at destination.
	 */
	public void move() throws IllegalStateException {
		Direction direction;

		if (location.getX() < destination.getX()) {
			direction = Direction.RIGHT;
		} else if (location.getX() > destination.getX()) {
			direction = Direction.LEFT;
		} else if (location.getY() < destination.getY()) {
			direction = Direction.UP;
		} else if (location.getY() > destination.getY()) {
			direction = Direction.DOWN;
		} else {
			// Bus is at destination
			throw new IllegalStateException("Tried to move bus when it is already at its destination. "
					+ "Make sure the bus has a destination not equal to its current location before trying to move it.");
		}

		location = city.oneOver(location, direction);
	}

	/**
	 * Picks up as many people as possible at location. Does nothing if no one is
	 * waiting at stop. Stops picking people up once capacity is reached.
	 */
	public void pickup() {
		// PriorityQueue based on time waiting at stop.
		PriorityQueue<Person> peopleAtLoc = city.getPeopleAtIntersection(location);

		while (peopleAtLoc.size() > 0 && passengers.size() <= capacity) {
			passengers.add(peopleAtLoc.poll());
		}
	}

	/**
	 * Returns the closest intersection that has a person waiting for a ride. If
	 * nobody is at the provided intersection, it checks adjacent ones.
	 * 
	 * @param intersection Intersection to check.
	 * @return Intersection of person waiting.
	 */
	public Intersection personSearch(Intersection intersection) {
		PriorityQueue<Intersection> closeIntersections = city.getClosestIntersections(intersection);

		for (int i = 0; i < closeIntersections.size(); i++) {
			Intersection check = closeIntersections.poll();
			if (city.getPeopleAtIntersection(check).size() > 0) {
				return check;
			}
		}

		// If everyone has been picked up by a bus, the bus should stay in the same location.
		return intersection;
	}
	
	/**
	 * Sets the destination to an intersection that has at least one person waiting for a ride.
	 */
	public void findPeople() {
		setDestination(personSearch(location));
	}

	/**
	 * @param destination The destination.
	 */
	protected void setDestination(Intersection destination) {
		this.destination = destination;
	}

	/**
	 * Determines destination based on algorithm implemented in child class. Then
	 * sets the destination instance variable to that.
	 */
	public abstract void generateDestination();

	/**
	 * @return the location.
	 */
	public Intersection getLocation() {
		return location;
	}

	/**
	 * @return the city.
	 */
	public City getCity() {
		return city;
	}

}
