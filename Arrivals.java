import java.util;

public class Arrivals extends SimObject {
    private double average;
    private int timeUntil;
    
    private double nextDoubleExp() {
        return (Math.log(1 - Random.nextDouble()) / ((-1.0)*average));
    }

    public void tick(int clock, Road road) {
        if (timeUntil == 0) {
            Car newCar = new Car(clock);
            timeUntil = Math.round(nextDoubleExp());
        } else {
            timeUntil--;
        }
    }

    public Arrivals(int clock, float avgArrivalPeriod) {
        super(clock);
        average = avgArrivalPeriod;
        timeUntil = Math.round(nextDoubleExp());
    }
}