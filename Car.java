import java.util;

public class Car extends SimObject {
    public static enum Movement {
        Forward, Left, Right;
    }

    private Movement movement;

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

    public Car(int clock, double probForward, double probRight) {
        super(clock);
        double randomSample = Random.nextDouble();
        resetMovement();
    }
}