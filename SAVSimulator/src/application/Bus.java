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

	protected void setDestination(Intersection destination) {
		this.destination = destination;
	}

	/**
	 * Determines destination based on algorithm implemented in child class. Then
	 * sets the destination instance variable to that.
	 */
	public abstract void generateDestination();
	
	/**
	 * Runs through all of the passengers' destinations to find the closest to the given intersection.
	 * 
	 * @param intersection Base intersection to compare all of the passengers' destinations to.
	 * @return The closest Intersection destination.
	 */
	private Intersection findClosestDestination(Intersection intersection) {
		// TODO: How do I make this method more abstract by not having it in two child classes but manage to make it work.
		Person chosen = passengers.get(0);
		
		for (int i = 1; i < passengers.size(); i++) {
			// Person to compare chosen to.
			Person other = passengers.get(i);
			
			// Distance of other's destination from current bus location.
			int otherDistance = getLocation().distanceFrom(other.getDestination());
			// Distance of chosen's distance from current bus location.
			int chosenDistance = getLocation().distanceFrom(chosen.getDestination());
			
			if (otherDistance < chosenDistance) {
				chosen = other;
			} else if (otherDistance == chosenDistance) {
				
				// If other has been on bus longer, prioritize them.
				if (other.getTimeInBus() > chosen.getTimeInBus()) {
					chosen = other;
				}
				
			}
		}
		
		return chosen.getDestination();
	}
	
	/**
	 * @return the location.
	 */
	public Intersection getLocation() {
		return location;
	}

}
