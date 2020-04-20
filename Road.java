import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

public class Road extends SimObject {
    private int arrivalDirection;
    private int laneLimit;

    private Queue<Car> laneA, laneB;
    private Arrivals arrival;

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

    public void assignArrival(Arrivals arrive, int direction) { arrival = arrive; }

    public void tick(int clock, List<Car> intersection) {
        if (arrival != null) {
            if (arrivalDirection == 0)
                arrival.tick(clock, laneA);
            else 
                arrival.tick(clock, laneB);
        }
    }

    public Road(int clock, int sizeLimit) {
        super(clock);

        laneA = new LinkedList<Car>();
        laneB = new LinkedList<Car>();

        laneLimit = sizeLimit;
        arrival = null;
    }
}