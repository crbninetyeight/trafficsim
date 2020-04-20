import java.util.Random;

public class Arrivals extends SimObject {
    private final double probForward = 0.6;
    private final double probRight = 0.3;

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
        return (Math.log(((1 - r.nextDouble()) / ((-1.0)*(1.0/denom)))));
    }

    private void newDenom(int clock) {
        denom = Math.round(denomMax - (denomMax - denomMin)*pdfNorm(clock));
    }

    public void tick(int clock, Queue<Car> lane) {
        System.out.println(timeUntil);
        if (timeUntil == 0) {
            lane.add(new Car(clock, probForward, probRight));
            newDenom(clock);
            timeUntil = Math.round(nextDoubleExp());
        } else {
            timeUntil--;
        }
    }

    public Arrivals(int clock, int maxAvg, int minAvg) {
        super(clock);
        denomMax = maxAvg; denomMin = minAvg;
        timeUntil = Math.round(nextDoubleExp());
    }
}