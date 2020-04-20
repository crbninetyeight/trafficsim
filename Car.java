import java.util.Random;

public class Car extends SimObject {
    private long countdown;

    private double probForward, probRight;

    public static enum Movement {
        Forward, Left, Right;
    }

    private Movement movement;
    private RoadIntersection.Direction directionFrom;
    private RoadIntersection.Direction directionGoing;

    public Movement getMovement() { return movement; }

    public void resetMovement() {
        Random r = new Random();
        double randomSample = r.nextDouble();

        if (randomSample > probRight) {
            if (randomSample <= probForward + probRight) {
                movement = Movement.Right;
            } else {
                movement = Movement.Left;
            }
        } else {
            movement = Movement.Forward;
        }
    }

    public RoadIntersection.Direction isFrom() { return directionFrom; }
    public void setFrom(RoadIntersection.Direction direction) { directionFrom = direction;}

    public RoadIntersection.Direction isGoing() { return directionGoing; }
    public void setGoing(RoadIntersection.Direction going) { directionGoing = going; }

    public long getCountdown() { return countdown; }
    public void setCountdown(long count) { countdown = count; }

    public void tick(int clock) {
        if (countdown >= 0) countdown--; else countdown = 0;
    }

    public Car(int clock, double probForward, double probRight) {
        super(clock);

        this.probForward = probForward;
        this.probRight = probRight;

        countdown = 0;
        resetMovement();
    }
}