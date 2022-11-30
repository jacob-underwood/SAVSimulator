package application;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javafx.application.Platform;

/**
 * Simulation driver.
 */
public class City implements Runnable {

	// List of all people in simulation; number changes as people reach their
	// destination and are thus removed from the simulation.
	private LinkedList<Person> people;
	// List of all buses; number does not change after instantiated.
	private ArrayList<LinearBus> buses;
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
		this.buses = new ArrayList<>();
		this.grid = new Intersection[15][15];
		this.step = 0;
	}

	@Override
	public void run() {
		try {
//			int step = 0;
//
//			while (step < 5) {

//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						runSimulation();
//					}
//				}).start();
				
				runSimulation();

				Thread.sleep(1000);

//				step++;
				
				System.out.println("here");

//			}
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

		for (LinearBus bus : buses) {
			if (people.size() == 0) {
				// TODO: Make this do something better than close out of program.
				System.out.println("Everyone has reached their destination!");
				System.exit(0);
			}
			
			// TODO: It might be more intuitive to have the bus pick up after it moves, but
			// also pick up once at the beginning of the simulation.
			int pickedUp = bus.pickup();
			for (int i = 0; i < pickedUp; i++) {
//				Platform.runLater(() -> {cityDisplay.removePerson(bus.getLocation().getX(), bus.getLocation().getY());});
				cityDisplay.removePerson(bus.getLocation().getX(), bus.getLocation().getY());
			}
			
			Intersection startLoc = bus.getLocation();
			Intersection dest = bus.getDestination();

			if (dest == null || startLoc.equals(dest)) {
				bus.generateDestination();
			}
			
			int startX = startLoc.getX();
			int startY = startLoc.getY();
			
			bus.move();
			
			Intersection newLoc = bus.getLocation();
			int newX = newLoc.getX();
			int newY = newLoc.getY();
			
//			Platform.runLater(() -> {cityDisplay.busMoved(startX, startY, newX, newY);});
			cityDisplay.busMoved(startX, startY, newX, newY);

		}

		return -1;
	}

	/**
	 * Creates all people and buses at the start of the program.
	 */
	public void setupSimulation() {
		// Create grid.
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				grid[j][i] = new Intersection(i, j);
			}
		}

		// Create people. 500
		for (int i = 0; i < 1; i++) {
			// Generate starting position.
			int randStartX = (int) (Math.random() * 15);
			int randStartY = (int) (Math.random() * 15);

			// Generate destination.
			int randDestX = (int) (Math.random() * 15);
			int randDestY = (int) (Math.random() * 15);

			cityDisplay.addPerson(randStartX, randStartY);

			Intersection start = grid[randStartY][randStartX];
			Intersection end = grid[randDestY][randDestX];
			Person person = new Person(start, end);
			
			System.out.println(person);

			people.add(person);
		}

		// Create buses. 50
		for (int i = 0; i < 1; i++) {
			// Generate starting position.
//			int randStartX = (int) (Math.random() * 15);
//			int randStartY = (int) (Math.random() * 15);
			int randStartX = 3;
			int randStartY = 2;
			
			cityDisplay.createBus(randStartX, randStartY);

			Intersection start = grid[randStartY][randStartX];
			LinearBus bus = new LinearBus(start, 20, this);

			buses.add(bus);
		}

	}

	/**
	 * Gets the intersection one over from the supplied intersection in the given
	 * direction.
	 * 
	 * @param intersection Intersection to use as starting location.
	 * @param direction    Direction returned Intersection should be in.
	 * @return Intersection one over in given direction.
	 * @throws ArrayIndexOutOfBoundsException If called on an edge or corner
	 *                                        intersection in the grid, one cannot
	 *                                        go off the grid.
	 */
	public Intersection oneOver(Intersection intersection, Direction direction) throws ArrayIndexOutOfBoundsException {
		switch (direction) {
		case LEFT:
			return grid[intersection.getY()][intersection.getX() - 1];
		case RIGHT:
			return grid[intersection.getY()][intersection.getX() + 1];
		case UP:
			return grid[intersection.getY() - 1][intersection.getX()];
		case DOWN:
			return grid[intersection.getY() + 1][intersection.getX()];
		default:
			throw new EnumConstantNotPresentException(direction.getClass(),
					"Valid enum constants for Direction include LEFT, RIGHT, UP, and DOWN");
		}
	}

	/**
	 * Returns list of everyone not in a bus at a certain intersection.
	 * 
	 * @param intersection Intersection to check for people at.
	 * @return People at Intersection in a PriorityQueue based on how long they have
	 *         been waiting.
	 */
	public PriorityQueue<Person> getPeopleAtIntersection(Intersection intersection) {
		PriorityQueue<Person> peopleAtIntersection = new PriorityQueue<>();

		for (Person person : people) {
			if (person.getLocation().equals(intersection) && !person.isInBus()) {
				peopleAtIntersection.add(person);
			}
		}

		return peopleAtIntersection;
	}

	/**
	 * Returns a PriorityQueue of Intersections in which intersections closer to the
	 * input are higher in priority.
	 * 
	 * @param intersection Intersection to find close intersections to.
	 * @return PriorityQueue of close Intersections.
	 */
	public PriorityQueue<Intersection> getClosestIntersections(Intersection primary) {
		RelativeIntersectionComparator intersectionComparator = new RelativeIntersectionComparator(primary);

		PriorityQueue<Intersection> closeIntersections = new PriorityQueue<>(grid.length * grid.length,
				intersectionComparator);
		
		// Did not use PriorityQueue.addAll because grid would have to be put into a
		// Collections object.
		for (Intersection[] intersections : grid) {
			for (Intersection intersection : intersections) {
				closeIntersections.add(intersection);
			}
		}

		return closeIntersections;
	}
	
	/**
	 * Removes person from people instance variable.
	 * 
	 * @param person Person to remove.
	 */
	public void removePerson(Person person) {
		System.out.println(people.size());
		people.remove(person);
		System.out.println(people.size());
	}

	/**
	 * @return the grid
	 */
	public Intersection[][] getGrid() {
		return grid;
	}

}
