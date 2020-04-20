import java.util.Random;

public class Arrivals extends SimObject {
    private int denomMax, denomMin;
    private int denom;
    private final double center = 61200;
    private final double sd = 3200;
    private int timeUntil;

    private double pdfNorm(int clock) {
        return (Math.pow(Math.E, -0.5*Math.pow((clock - center)/sd, 2)));
    }

    private double nextDoubleExp() {
        Random r = new Random();
        return (Math.log(((1 - r.nextDouble()) / ((-1.0)*(1.0/denom)))));
    }

    private void newDenom(int clock) {
        denom = denomMax - (denomMax - denomMin)*pdfNorm(clock);
    }

    public void tick(int clock, Road road) {
        if (timeUntil == 0) {
            Car newCar = new Car(clock);
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