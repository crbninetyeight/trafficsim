import java.util.Queue;
import java.util.List;

public class Road extends SimObject {
    private int laneLimit;
    private Queue<Car> laneA, laneB;
    private Arrivals arrival;

    public boolean isLaneFullA() { return laneA.size() == laneLimit; }
    public boolean isLaneFullB() { return laneB.size() == laneLimit; }

    public void assignArrival(Arrivals arrive) { arrival = arrive; }

    public void tick(int clock, List<Car> intersection) {
        if (arrival != null) {

        }
    }

    public Road(int clock, int sizeLimit) {
        super(clock);
        laneLimit = sizeLimit;
        arrival = null;
    }
}