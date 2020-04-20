
public class TrafficSignal extends SimObject {
    private int trafficInterval;
    private boolean on;

    public boolean NorthSouth() { return on; }

    public void tick(int clock) {
        if (clock % trafficInterval == 0) {
            on = !on;
        }
    }

    public TrafficSignal(int clock, int interval) {
        super(clock);
        on = false;
        trafficInterval = interval;
    }
}