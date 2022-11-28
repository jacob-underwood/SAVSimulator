package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Simulation driver.
 */
public class City implements Runnable {

	// List of all people in simulation; number changes as people reach their
	// destination and are thus removed from the simulation.
	private LinkedList<Person> people;
	// List of all buses; number does not change after instantiated.
	private Bus[] buses;
	// All Intersections in a city.
	private Intersection[][] grid;
	
	// Current step (time) the simulation is on. For example, each step could be 1
	// minute.
	private int step;

	// Stores CityDisplay object to manipulate graphical output.
	private CityDisplay cityDisplay;

	public City(CityDisplay cityDisplay) {
		this.cityDisplay = cityDisplay;
		
		this.people = new LinkedList<Person>();
		this.buses = new Bus[30];
		this.grid = new Intersection[15][15];
		this.step = 0;
	}
	
	@Override
	public void run() {
		try {
			int step = 0;
			
			while (step < 100) {
				
				runSimulation();
			
				Thread.sleep(10000);
				
				step++;
				
			}
		} catch (InterruptedException e) {
			System.err.print(e);
		}
	}

	/**
	 * Runs simulation by 1. Moving buses 2. Having buses pick up people 3. Setting
	 * buses' destinations 4. Handling all other fundamental tasks in a step-wise
	 * manner
	 * 
	 * @return Current step.
	 */
	public int runSimulation() {
		
		
		
		return -1;
	}

	/**
	 * Creates all people and buses at the start of the program.
	 */
	public void setupSimulation() {
		
		// Create people
		int peopleCount = 0;
		while (peopleCount < 0) {
			cityDisplay.addPerson(peopleCount, peopleCount);
			peopleCount++;
		}
		
	}
	
	/**
	 * Gets the intersection one over from the supplied intersection in the given direction.
	 * 
	 * @param intersection Intersection to use as starting location.
	 * @param direction Direction returned Intersection should be in.
	 * @return Intersection one over in given direction.
	 * @throws ArrayIndexOutOfBoundsException If called on an edge or corner intersection in the grid, one cannot go off the grid.
	 */
	public Intersection oneOver(Intersection intersection, Direction direction) throws ArrayIndexOutOfBoundsException {
		switch (direction) {
			case LEFT: return grid[intersection.getY()][intersection.getX() - 1];
			case RIGHT: return grid[intersection.getY()][intersection.getX() + 1];
			case UP: return grid[intersection.getY() - 1][intersection.getX()];
			case DOWN: return grid[intersection.getY() + 1][intersection.getX()];
			default: throw new EnumConstantNotPresentException(direction.getClass(), "Valid enum constants for Direction include LEFT, RIGHT, UP, and DOWN");
		}
	}
	
	/**
	 * Returns list of everyone not in a bus at a certain intersection.
	 * 
	 * @param intersection Intersection to check for people at.
	 * @return People at Intersection in a PriorityQueue based on how long they have been waiting.
	 */
	public PriorityQueue<Person> getPeopleAtIntersection(Intersection intersection) {
		PriorityQueue<Person> peopleAtIntersection = new PriorityQueue<>();
		
		for (Person person : people) {
			// TODO: Make Intersection.equals?
			if (person.getLocation().equals(intersection)) {
				peopleAtIntersection.add(person);
			}
		}
		
		return peopleAtIntersection;
	}
	
	/**
	 * Returns a PriorityQueue of Intersections in which intersections closer to the input are higher in priority.
	 * 
	 * @param intersection Intersection to find close intersections to.
	 * @return PriorityQueue of close Intersections.
	 */
	public PriorityQueue<Intersection> getClosestIntersections(Intersection primary) {
		RelativeIntersectionComparator intersectionComparator = new RelativeIntersectionComparator(primary);
		
		PriorityQueue<Intersection> closeIntersections = new PriorityQueue<Intersection>(grid.length * grid.length, intersectionComparator);
		
		// Did not use PriorityQueue.addAll because grid would have to be put into a Collections object.
		for (Intersection[] intersections : grid) {
			for (Intersection intersection : intersections) {
				closeIntersections.add(intersection);
			}
		}
		
		return closeIntersections;
	}
	
	/**
	 * @return the grid
	 */
	public Intersection[][] getGrid() {
		return grid;
	}

}
