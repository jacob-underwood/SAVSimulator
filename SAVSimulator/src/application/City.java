package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Simulation driver.
 */
public class City<T extends Bus<?>> {

	// List of all people in simulation; number changes as people reach their
	// destination and are thus removed from the simulation.
	private LinkedList<Person> people;
	// List of all buses; number does not change after instantiated.
	private ArrayList<T> buses;
	// All Intersections in a city.
	private Intersection[][] grid;
	
	// Class for buses (LinearBus, AverageBus, ProximityBus)
	private Class<T> typeOfBus;

	// Current step (time) the simulation is on. For example, each step could be 1
	// minute.
	private int step;

	// Stores CityDisplay object to manipulate graphical output.
	private CityDisplay cityDisplay;
	// If true, displays simulation in graphical output.
	private boolean display;

	public City(CityDisplay cityDisplay, Class<T> typeOfBus, boolean display) {
		this.cityDisplay = cityDisplay;
		this.typeOfBus = typeOfBus;
		this.display = display;

		this.people = new LinkedList<Person>();
		this.buses = new ArrayList<>();
		this.grid = new Intersection[15][15];
		this.step = 0;
	}

	/**
	 * Runs simulation by 1. Moving buses 2. Having buses pick up people 3. Setting
	 * buses' destinations 4. Handling all other fundamental tasks in a step-wise
	 * manner
	 * 
	 * @return Next step or -1 if there is no next step.
	 */
	public int runSimulation() {
		ArrayList<T> busesToRemove = new ArrayList<>();

		for (T bus : buses) {
			if (people.size() == 0) {
				// TODO: Make this do something better than print/close out of program.
				String[] busTypeClassInfo = typeOfBus.toString().split("[.]");
				String busType = busTypeClassInfo[busTypeClassInfo.length - 1];
				System.out.println("Everyone has reached their destination! Bus Type: " + busType + ". Steps: " + step + ".");
//				System.exit(0);
				return -1;
			}

			// TODO: It might be more intuitive to have the bus pick up after it moves, but
			// also pick up once at the beginning of the simulation.
			if (display) {
				int pickedUp = bus.pickup();
				for (int i = 0; i < pickedUp; i++) {
//				Platform.runLater(() -> {cityDisplay.removePerson(bus.getLocation().getX(), bus.getLocation().getY());});
					cityDisplay.removePerson(bus.getLocation().getX(), bus.getLocation().getY());
				}
			} else {
				bus.pickup();
			}

			Intersection startLoc = bus.getLocation();
			Intersection dest = bus.getDestination();

			if (dest == null || startLoc.equals(dest)) {
				bus.generateDestination();
			}

			int startX = startLoc.getX();
			int startY = startLoc.getY();

			if (bus.getDestination() != bus.getLocation()) {
				bus.move();
				
				Intersection newLoc = bus.getLocation();
				int newX = newLoc.getX();
				int newY = newLoc.getY();

//				Platform.runLater(() -> {cityDisplay.busMoved(startX, startY, newX, newY);});
				if (display) {
					cityDisplay.busMoved(startX, startY, newX, newY);
				}
				
			} else {
				if (display) {
					int finishedBusX = bus.getLocation().getX();
					int finishedBusY = bus.getLocation().getY();
					cityDisplay.removeBus(finishedBusX, finishedBusY);
					
					busesToRemove.add(bus);
				}
				
				
			}
			
		}
		
		for (T bus : busesToRemove) {
			buses.remove(bus);
		}

		step++;

		return step;
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
		for (int i = 0; i < 500; i++) {
			// Generate starting position.
			int randStartX = (int) (Math.random() * 15);
			int randStartY = (int) (Math.random() * 15);
//			int randStartX = 3;
//			int randStartY = 2;

			// Generate destination.
			int randDestX;
			int randDestY;

			do {
				randDestX = (int) (Math.random() * 15);
				randDestY = (int) (Math.random() * 15);
			} while (randDestX == randStartX || randDestY == randStartY);

			if (display) {
				cityDisplay.addPerson(randStartX, randStartY);
			}
			
			Intersection start = grid[randStartY][randStartX];
			Intersection end = grid[randDestY][randDestX];
//			System.out.println("Person's destination: " + end);
			Person person = new Person(start, end);

//			System.out.println(person);

			people.add(person);
		}

		// Create buses. 50
		for (int i = 0; i < 50; i++) {
			// Generate starting position.
			int randStartX = (int) (Math.random() * 15);
			int randStartY = (int) (Math.random() * 15);
//			int randStartX = 3;
//			int randStartY = 2;

			if (display) {
				cityDisplay.createBus(randStartX, randStartY);
			}
			
			Intersection start = grid[randStartY][randStartX];
//			T bus = new T(start, 20, this);
			T bus = null;
			
			Class<?>[] constructorArgs = new Class[3];
			constructorArgs[0] = Intersection.class;
			constructorArgs[1] = int.class;
			constructorArgs[2] = City.class;
			
			try {
				bus = (T) typeOfBus.getDeclaredConstructor(constructorArgs).newInstance(start, 20, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			

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
//		System.out.println(people.size());
		people.remove(person);
//		System.out.println(people.size());
	}

	/**
	 * @return the grid
	 */
	public Intersection[][] getGrid() {
		return grid;
	}

}
