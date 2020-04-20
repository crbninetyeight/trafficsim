
public class RoadNet {
    private RoadIntersection intersectionA, intersectionB;

    public void tick(int clock) {
        intersectionA.tick(clock);
        intersectionB.tick(clock);
    }

    public RoadNet(int clock) {
        intersectionA = new RoadIntersection(clock, 80);
        intersectionB = new RoadIntersection(clock, 120);

        intersectionA.join(intersectionB);
    }
}