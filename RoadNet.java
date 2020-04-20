
public class RoadNet {
    private RoadIntersection intersectionA, intersectionB;

    public void tick(int clock) {
        intersectionA.tick(clock);
        intersectionB.tick(clock);
    }

    public RoadNet(int clock) {
        intersectionA = new RoadIntersection(clock);
        intersectionB = new RoadIntersection(clock);

        intersectionA.join(intersectionB);
    }
}