
public class Road extends SimObject {
    private int laneLimit;
    private Queue<Car> laneForward, laneLeft;

    public Road(int clock, int sizeLimit) {
        super(clock);
        laneLimit = sizeLimit;
    }
}