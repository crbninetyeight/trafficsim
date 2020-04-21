import java.util.ArrayList;

public class RoadNet {
    private RoadIntersection intersectionA, intersectionB;

    public void tick(int clock) {
        intersectionA.tick(clock);
        intersectionB.tick(clock);
    }

    public int getTotalCreated() {
        ArrayList<Arrivals> allArrivals = getAllArrivals();
        int totalCreations = 0;

        for (Arrivals arr : allArrivals) {
            totalCreations += arr.getTotalCreated();
        }

        return totalCreations;
    }

    public int getTotalTerminated() {
        ArrayList<Terminals> allTerms = getAllTerminals();
        int totalTerminations = 0;

        for (Terminals term : allTerms) {
            totalTerminations += term.getTotalTerminated();
        }

        return totalTerminations;
    }

    public double getAverageSoujournTime() {
        ArrayList<Terminals> allTerms = getAllTerminals();

        int totalSoujourn = 0;
        int totalTerminations = 0;
        for (Terminals term : allTerms) {
            totalSoujourn += term.getTotalSoujourn();
            totalTerminations += term.getTotalTerminated();
        }

        return totalSoujourn / totalTerminations;
    }

    public ArrayList<Arrivals> getAllArrivals() {
        ArrayList<Arrivals> ret = new ArrayList<Arrivals>();

        ret.addAll(intersectionA.getAllArrivals());
        ret.addAll(intersectionB.getAllArrivals());

        return ret;
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