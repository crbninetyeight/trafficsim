
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

import java.util.Random;
import java.util.Queue;

// Terminals - the terminating points in the simulation
public class Terminals extends SimObject {
    Queue<Car> lane;
    private double avg; // the average soujourn time

    private int totalTerminated; // number of cars terminated at this terminal
    private int totalTerminatedRH; // number of cars terminated at rush-hour

    private long totalSoujourn;  // total sum of soujourn time
    private long totalSoujournRH;  // total sum of soujourn time during rush-hour

    private long timeUntilNextTerm; // random variable determining time until next termination

    // outputs a random double with exponential distribution
    private double nextDoubleExp() {
        Random r = new Random();
        double randomSample = r.nextDouble();
        double a = (1.0 - randomSample);
        // System.out.println(1.0/(double)denom);
        // System.out.println(randomSample);
        // System.out.println(a);
        // System.out.println((-1.0) * Math.log(a) / (1.0/(double)denom));
        return (-1.0) * Math.log(a) / (avg);
    }

    public double averageSoujourn() { return totalSoujourn / totalTerminated; }

    public long getTotalSoujourn() { return totalSoujourn; }
    public long getTotalSoujournRH() { return totalSoujournRH; }

    public int getTotalTerminated() { return totalTerminated; }
    public int getTotalTerminatedRH() { return totalTerminatedRH; }

    // perform a step of simulation
    public void tick(int clock) {
        if (!lane.isEmpty()) {
            // determine if it time until next termination has elapsed
            if (timeUntilNextTerm == 0) {
                Car car = lane.poll();

                if (clock > 57000 && clock < 64000) {
                    // increase number created during rush-hour traffic
                    totalTerminatedRH++;
                    totalSoujournRH += clock - car.getCreationTime();
                }

                // System.out.println("" + clock + ": Removing...");
                totalSoujourn += clock - car.getCreationTime();
                totalTerminated++;
                timeUntilNextTerm = Math.round(nextDoubleExp());
            } else timeUntilNextTerm--;
        }
    }

    public Terminals(int clock, Queue<Car> lane, double average) {
        super(clock);
        avg = average;
        this.lane = lane;
        timeUntilNextTerm = Math.round(nextDoubleExp());

        totalTerminated     = 0;
        totalTerminatedRH   = 0;
        totalSoujourn       = 0;
        totalSoujournRH     = 0;
    }
}