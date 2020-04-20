import java.util.HashMap;
import java.util.ArrayList;

public class RoadIntersection extends SimObject {
    private final int ROAD_SIZE = 50;

    public static enum Direction {
        North, South, East, West
    }

    private HashMap<Direction, Road> roads;
    private TrafficSignal trafficSignal;
    private ArrayList<Car> intersection;

    protected void passRoadRef(Road ref) { roads.put(Direction.East, ref); }

    public void join(RoadIntersection ref) {
        ref.passRoadRef(roads.get(Direction.West));
    }

    public void tick(int clock) {
        ArrayList<Car> toRemove = new ArrayList<Car>();

        for (Car car : intersection) {
            car.tick(clock);
            if (car.getCountdown() == 0) {
                Car.Movement movement = car.getMovement();

                car.resetMovement();

                switch (car.isGoing()) {
                    case North:
                    roads.get(Direction.South).appendA(car);
                    break;

                    case South:
                    roads.get(Direction.North).appendB(car);
                    break;

                    case East:
                    roads.get(Direction.West).appendB(car);
                    break;

                    case West:
                    roads.get(Direction.South).appendA(car);
                    break;
                }

                toRemove.add(car);
            }
        }

        for (Car car : toRemove) {
            intersection.remove(car);
        }

        trafficSignal.tick(clock);

        for (RoadIntersection.Direction dir : roads.keySet()) {
            Road road = roads.get(dir);
            road.tick(clock, intersection);
            if (road.hasCarInA()) {
                Car car;

                if (dir == Direction.North || dir == Direction.East) {
                    car = road.getFromA();
                } else {
                    car = road.getFromB(); 
                }

                car.setFrom(dir);
                setCarDirection(car);
                
                if (car.getMovement() != Car.Movement.Right) {
                    if (car.isFrom() == Direction.North || car.isFrom() == Direction.South) {
                        if (trafficSignal.NorthSouth()) {
                            boolean isMatched = false;
                            for (Car ref : intersection) {
                                isMatched = car.isGoing() == ref.isGoing();
                                if (isMatched) break;
                            }

                            if (!isMatched) {
                                intersection.add(car);
                                road.removeTopA();
                            }
                        }
                    }
                } else {
                    boolean isMatched = false;
                    for (Car ref : intersection) {
                        isMatched = car.isGoing() == ref.isGoing();
                        if (isMatched) break;
                    }

                    if (!isMatched) {
                        intersection.add(car);
                        road.removeTopA();
                    }
                }
            }
        }
    }

    public void setCarDirection(Car car) {
        switch (car.getMovement()) {
            case Forward:
            switch (car.isFrom()) {
                case North:
                car.setGoing(RoadIntersection.Direction.South);
                break;

                case South:
                car.setGoing(RoadIntersection.Direction.North);
                break;

                case East:
                car.setGoing(RoadIntersection.Direction.West);
                break;

                case West:
                car.setGoing(RoadIntersection.Direction.East);
                break;
            }
            break;

            case Right:
            switch (car.isFrom()) {
                case North:
                car.setGoing(RoadIntersection.Direction.West);
                break;

                case South:
                car.setGoing(RoadIntersection.Direction.East);
                break;

                case East:
                car.setGoing(RoadIntersection.Direction.North);
                break;

                case West:
                car.setGoing(RoadIntersection.Direction.South);
                break;
            }
            break;

            case Left:
            switch (car.isFrom()) {
                case North:
                car.setGoing(RoadIntersection.Direction.East);
                break;

                case South:
                car.setGoing(RoadIntersection.Direction.West);
                break;

                case East:
                car.setGoing(RoadIntersection.Direction.South);
                break;

                case West:
                car.setGoing(RoadIntersection.Direction.North);
                break;
            }
            break;
        }
    }
    public RoadIntersection(int clock, int trafficInterval) {
        super(clock);

        intersection = new ArrayList<Car>();
        roads = new HashMap<Direction, Road>();
        roads.put(Direction.North, new Road(clock, ROAD_SIZE));
        roads.put(Direction.South, new Road(clock, ROAD_SIZE));
        roads.put(Direction.East, new Road(clock, ROAD_SIZE));
        roads.put(Direction.West, new Road(clock, ROAD_SIZE));

        trafficSignal = new TrafficSignal(clock, trafficInterval);
    }
}