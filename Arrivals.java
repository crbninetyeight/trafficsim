import java.util.Random;
import java.util.Queue;

public class Arrivals extends SimObject {
    private final double probForward = 0.6;
    private final double probRight = 0.3;

    private int numberCreated;

    private int denomMax, denomMin;
    private long denom;
    private final double center = 61200;
    private final double sd = 3200;
    private long timeUntil;

    private double pdfNorm(int clock) {
        return (Math.pow(Math.E, -0.5*Math.pow((clock - center)/sd, 2)));
    }

    private double nextDoubleExp() {
        Random r = new Random();
        double randomSample = r.nextDouble();
        double a = (1.0 - randomSample);
        // System.out.println(1.0/(double)denom);
        // System.out.println(randomSample);
        // System.out.println(a);
        // System.out.println((-1.0) * Math.log(a) / (1.0/(double)denom));
        return (-1.0) * Math.log(a) / (1.0/(double)denom);
    }

    private void newDenom(int clock) {
        denom = Math.round(denomMax - (denomMax - denomMin)*pdfNorm(clock+1));
    }

    public void tick(int clock, Queue<Car> lane) {
        if (timeUntil < 0) timeUntil = 0;
        if (timeUntil == 0) {
            if (lane.size() < 30) {
                numberCreated++;
                // System.out.println("" + clock + ": Adding...");
                lane.add(new Car(clock, probForward, probRight));
                newDenom(clock);
                timeUntil = Math.round(nextDoubleExp());
            }
        } else {
            timeUntil--;
        }
    }

    public Arrivals(int clock, int maxAvg, int minAvg) {
        super(clock);
        numberCreated = 0;
        denomMax = maxAvg; denomMin = minAvg;
        newDenom(clock);
        timeUntil = Math.round(nextDoubleExp());
    }
}