import java.util.ArrayList;

public class RoadNet {
    private RoadIntersection intersectionA, intersectionB;

    public void tick(int clock) {
        intersectionA.tick(clock);
        intersectionB.tick(clock);
    }

    public double getAverageSoujournTime() {
        ArrayList<Terminals> allTerms = getAllTerminals();

        System.out.println(allTerms);

        int totalSoujourn = 0;
        int totalTerminations = 0;
        for (Terminals term : allTerms) {
            totalSoujourn += term.getTotalSoujourn();
            totalTerminations += term.getTotalTerminated();
        }

        return totalSoujourn / totalTerminations;
    }

    public ArrayList<Terminals> getAllTerminals() {
        ArrayList<Terminals> ret = new ArrayList<Terminals>();

        ret.addAll(intersectionA.getAllTerminals());
        ret.addAll(intersectionB.getAllTerminals());

        return ret;
    }

    public RoadNet(int clock) {
        intersectionA = new RoadIntersection(clock, 80);
        intersectionB = new RoadIntersection(clock, 120);

        intersectionA.join(intersectionB);
    }
}