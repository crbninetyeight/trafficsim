
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

// Road - road object
public class Road extends SimObject {
    private int arrivalDirection; // direction cars are arriving from
    private int laneLimit; // the maximum number of cars allowed in a lane

    private Queue<Car> laneA, laneB;

    private Arrivals arrival;
    private Terminals terminal;

    public boolean hasCarInA() { return !laneA.isEmpty(); }
    public boolean hasCarInB() { return !laneB.isEmpty(); }

    public boolean isLaneFullA() { return laneA.size() == laneLimit; }
    public boolean isLaneFullB() { return laneB.size() == laneLimit; }

    public void appendA(Car car) { laneA.add(car); }
    public void appendB(Car car) { laneB.add(car); }

    public Car getFromA() { return laneA.peek(); }
    public Car getFromB() { return laneB.peek(); }

    public void removeTopA() { laneA.poll(); }
    public void removeTopB() { laneB.poll(); }

    public void assignArrival(Arrivals arrive, int direction) { 
        arrival = arrive; 
        arrivalDirection = direction;
    }
    public void unassignArrival() { arrival = null; }

    public void assignTerminal(int clock, double avg, int direction) {
        if (direction == 0) {
            terminal = new Terminals(clock, laneA, avg);
        } else { terminal = new Terminals(clock, laneB, avg); }
    }
    public void unassignTerminal() { terminal = null; }

    public int totalCurrentSize() {
        return laneA.size() + laneB.size();
    }

    public int laneASize() { return laneA.size(); }
    public int laneBSize() { return laneB.size(); }

    public void tick(int clock, List<Car> intersection) {
        if (arrival != null) {
            if (arrivalDirection == 0)
                arrival.tick(clock, laneA);
            else 
                arrival.tick(clock, laneB);
        }

        if (terminal != null) {
            terminal.tick(clock);
        }
    }

    public Arrivals getArrival() { return arrival; }
    public Terminals getTerminal() { return terminal; }

    public Road(int clock, int sizeLimit) {
        super(clock);

        laneA = new LinkedList<Car>();
        laneB = new LinkedList<Car>();

        laneLimit = sizeLimit;
        arrival = null;
    }
}