
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

/* TrafficSignal
 * Simulation object modeling a traffic signal.
 * Has two states (denoted by 'on') which indicates how
 * which direction is allowed.
 */
public class TrafficSignal extends SimObject {
    private int trafficInterval;    // variable indicating time to toggle states
    private boolean on;             // true - NorthSouth travel; south - WestEast travel;

    // if true, north-south travel is permitted. else, east-west travel is permitted.
    public boolean NorthSouth() { return on; }

    // perform a simulated step
    public void tick(int clock) {
        // toggle after time interval elapsed
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