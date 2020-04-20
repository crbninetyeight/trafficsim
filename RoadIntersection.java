
public class RoadIntersection extends SimObject {
    private Road roads[];

    protected void passRoadRef(Road ref) { roads[3] = ref; }

    public void join(RoadIntersection ref) {
        ref.passRoadRef(roads[1]);
    }

    public RoadIntersection(int clock) {
        super(clock);
        roads = new Road[4];
    }
}