
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

import java.util.Random;

// Car - an object which simulates a car
public class Car extends SimObject {
    // amount of time left in an intersection
    private long countdown;

    // probability of going forward, right
    private double probForward, probRight;

    public static enum Movement {
        Forward, Left, Right;
    }

    private Movement movement;
    private RoadIntersection.Direction directionFrom;
    private RoadIntersection.Direction directionGoing;

    public Movement getMovement() { return movement; }

    // decide where car is moving next
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