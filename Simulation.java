
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
        System.out.println("Average soujourn time: " + roadNetwork.getAverageSoujournTime());
    }
}