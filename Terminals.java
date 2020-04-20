import java.util.Random;
import java.util.Queue;

public class Terminals extends SimObject {
    Queue<Car> lane;
    private double avg;

    private int totalTerminated;
    private long totalSoujourn;

    private long timeUntilNextTerm;

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
    public int getTotalTerminated() { return totalTerminated; }

    public void tick(int clock) {
        if (!lane.isEmpty()) {
            if (timeUntilNextTerm == 0) {
                // System.out.println("" + clock + ": Removing...");
                Car car = lane.poll();
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

        totalTerminated = 0;
        totalSoujourn = 0;
    }
}