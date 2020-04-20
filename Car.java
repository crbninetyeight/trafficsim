public class Car extends SimObject {
    private int countdown;

    public static enum Movement {
        Forward, Left, Right;
    }

    private Movement movement;
    private RoadIntersection.Direction directionFrom;
    private RoadIntersection.Direction directionGoing;

    public Movement getMovement() { return movement; }
    public void resetMovement() {
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

    public int getCountdown() { return countdown; }
    public void setCountdown(int count) { countdown = count; }

    public Car(int clock, double probForward, double probRight) {
        super(clock);
        double randomSample = Random.nextDouble();
        countdown = 0;
        resetMovement();
    }
}