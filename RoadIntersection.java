import java.util.HashMap;
import java.util.List;

public class RoadIntersection extends SimObject {
    private static int trafficInterval = 20;

    public static enum Direction {
        North, South, East, West
    }

    private HashMap<Direction, Road> roads;
    private TrafficSignal trafficSignal;
    private List<Car> intersection;

    protected void passRoadRef(Road ref) { roads.put(Direction.East, ref); }

    public void join(RoadIntersection ref) {
        ref.passRoadRef(roads.get(Direction.West));
    }

    public void tick(int clock) {
        List<Car> toRemove;

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
    public RoadIntersection(int clock) {
        super(clock);

        roads.put(Direction.North, new Road(ROAD_SIZE, Direction.North));
        roads.put(Direction.South, new Road(ROAD_SIZE, Direction.South));
        roads(Direction.East, new Road(ROAD_SIZE, Direction.East));
        roads(Direction.West, new Road(ROAD_SIZE, Direction.West));

        trafficSignal = new TrafficSignal(clock, trafficInterval);
    }
}