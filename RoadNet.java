
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

import java.util.ArrayList;


// RoadNet - a road network consisting of two inter-connected intersections
public class RoadNet {
    private RoadIntersection intersectionA, intersectionB;

    public void tick(int clock) {
        // update the state of intersections A and B
        intersectionA.tick(clock);
        intersectionB.tick(clock);
    }

    // sum all creation counts recorded by the Arrivals objects into a single variable
    public int getTotalCreated() {
        ArrayList<Arrivals> allArrivals = getAllArrivals();
        int totalCreations = 0;

        for (Arrivals arr : allArrivals) {
            totalCreations += arr.getTotalCreated();
        }

        return totalCreations;
    }

    // sum all creation counts recorded by the Arrivals objects into a single variable
    public int getTotalCreatedRH() {
        ArrayList<Arrivals> allArrivals = getAllArrivals();
        int totalCreations = 0;

        for (Arrivals arr : allArrivals) {
            totalCreations += arr.getTotalCreatedRH();
        }

        return totalCreations;
    }

    // sum all termination counts recorded by the Terminals objects into a single variable
    public int getTotalTerminated() {
        ArrayList<Terminals> allTerms = getAllTerminals();
        int totalTerminations = 0;

        for (Terminals term : allTerms) {
            totalTerminations += term.getTotalTerminated();
        }

        return totalTerminations;
    }

    // sum all termination counts recorded by the Terminals objects into a single variable
    public int getTotalTerminatedRH() {
        ArrayList<Terminals> allTerms = getAllTerminals();
        int totalTerminations = 0;

        for (Terminals term : allTerms) {
            totalTerminations += term.getTotalTerminatedRH();
        }

        return totalTerminations;
    }

    // calculate the average soujourn time of the cars in the simulation
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

    // calculate the average soujourn time of the cars in the simulation
    public double getAverageSoujournTimeRH() {
        ArrayList<Terminals> allTerms = getAllTerminals();

        int totalSoujourn = 0;
        int totalTerminations = 0;
        for (Terminals term : allTerms) {
            totalSoujourn += term.getTotalSoujournRH();
            totalTerminations += term.getTotalTerminatedRH();
        }

        return totalSoujourn / totalTerminations;
    }

    // get an array of all Arrivals objects in the road network
    public ArrayList<Arrivals> getAllArrivals() {
        ArrayList<Arrivals> ret = new ArrayList<Arrivals>();

        ret.addAll(intersectionA.getAllArrivals());
        ret.addAll(intersectionB.getAllArrivals());

        return ret;
    }

    // get an array of all Terminals objects in the road network
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