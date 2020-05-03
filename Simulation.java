
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

// Simulation - the parent object of the entire simulation being modeled
public class Simulation {
    public static void main(String args[]) {
        int clock = 0;

        final int SECONDS = 24*60*60;
        RoadNet roadNetwork = new RoadNet(clock);

        while (clock < SECONDS) {
            roadNetwork.tick(clock++);
            if (clock % 1000 == 0) {
                System.out.println(clock);
            }
        }

        System.out.println("Total number of cars created: " + roadNetwork.getTotalCreated());
        System.out.println("Total number of cars soujourned: " + roadNetwork.getTotalTerminated());
        System.out.println("Average soujourn time (s): " + roadNetwork.getAverageSoujournTime());

        System.out.println();

        System.out.println("Total number of cars created (during rush-hour): " + roadNetwork.getTotalCreatedRH());
        System.out.println("Total number of cars soujourned (during rush-hour): " + roadNetwork.getTotalTerminatedRH());
        System.out.println("Average soujourn time (during rush-hour) (s): " + roadNetwork.getAverageSoujournTimeRH());
    }
}