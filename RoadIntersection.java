import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

public class RoadIntersection extends SimObject {
    private final int ROAD_SIZE = 50;

    public static enum Direction {
        North, South, East, West
    }

    private HashMap<Direction, Road> roads;
    private TrafficSignal trafficSignal;
    private ArrayList<Car> intersection;

    private double nextDoubleExp(double average) {
        Random r = new Random();
        double randomSample = r.nextDouble();
        double a = (1.0 - randomSample);
        return (-1.0) * Math.log(a) / (average);
    }

    protected void passRoadRef(Road ref) { roads.put(Direction.East, ref); }

    public void join(RoadIntersection ref) {
        roads.get(Direction.West).unassignArrival();
        roads.get(Direction.West).unassignTerminal();
        ref.passRoadRef(roads.get(Direction.West));
    }

    public void tick(int clock) {
        ArrayList<Car> toRemove = new ArrayList<Car>();

        for (Car car : intersection) {
            car.tick(clock);
            if (car.getCountdown() == 0) {
                car.resetMovement();

                switch (car.isGoing()) {
                    case North:
                    roads.get(Direction.South).appendB(car);
                    break;

                    case South:
                    roads.get(Direction.North).appendA(car);
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
            // System.out.println(intersection);
            intersection.remove(car);
        }

        toRemove.clear();
        trafficSignal.tick(clock);

        for (RoadIntersection.Direction dir : roads.keySet()) {
            Road road = roads.get(dir);
            road.tick(clock, intersection);

            Car car = null;

            if (dir == Direction.North || dir == Direction.East) {
                if (road.hasCarInA()) {
                    car = road.getFromA();
                }
            } else {
                if (road.hasCarInB()) {
                    car = road.getFromB();
                }
            }

            if (car != null) {
                boolean moveSuccess = false;

                car.setFrom(dir);
                setCarDirection(car);

                if (car.getMovement() != Car.Movement.Right) {
                    if (car.isFrom() == Direction.North || car.isFrom() == Direction.South) {
                        if (trafficSignal.NorthSouth()) {
                            boolean isMatched = false;
                            for (Car ref : intersection) {
                                isMatched = !isLegalMove(car, ref);
                                if (isMatched) break;
                            }

                            boolean isLaneFull = true;

                            if (car.isGoing() == Direction.North || car.isGoing() == Direction.East) {
                                isLaneFull = roads.get(car.isGoing()).isLaneFullB();
                            } else isLaneFull = roads.get(car.isGoing()).isLaneFullA();

                            if (!isMatched && !isLaneFull) {
                                car.setCountdown(Math.round(nextDoubleExp(0.5)));
                                intersection.add(car);
                                moveSuccess = true;
                                // road.removeTopA();
                            }
                        }
                    } else {
                        if (!trafficSignal.NorthSouth()) {
                            boolean isMatched = false;
                            for (Car ref : intersection) {
                                isMatched = !isLegalMove(car, ref);
                                if (isMatched) break;
                            }

                            boolean isLaneFull = true;

                            if (car.isGoing() == Direction.North || car.isGoing() == Direction.East) {
                                isLaneFull = roads.get(car.isGoing()).isLaneFullB();
                            } else isLaneFull = roads.get(car.isGoing()).isLaneFullA();

                            if (!isMatched && !isLaneFull) {
                                car.setCountdown(Math.round(nextDoubleExp(0.5)));
                                intersection.add(car);
                                moveSuccess = true;
                                // road.removeTopA();
                            }
                        }
                    }
                } else {
                    boolean isMatched = false;
                    for (Car ref : intersection) {
                        isMatched = !isLegalMove(car, ref);
                        if (isMatched) break;
                    }

                    boolean isLaneFull = true;

                    if (car.isGoing() == Direction.North || car.isGoing() == Direction.East) {
                        isLaneFull = roads.get(car.isGoing()).isLaneFullB();
                    } else isLaneFull = roads.get(car.isGoing()).isLaneFullA();

                    if (!isMatched && !isLaneFull) {
                        car.setCountdown(Math.round(nextDoubleExp(0.5)));
                        intersection.add(car);
                        moveSuccess = true;
                        // road.removeTopA();
                    }
                }

                if (moveSuccess) {
                    // System.out.println("Move was successful!");
                    if (dir == Direction.North || dir == Direction.East) {
                        road.removeTopA();
                    } else road.removeTopB();
                }
            }


            // if (road.hasCarInA()) {
            //     Car car;

            //     if (dir == Direction.North || dir == Direction.East) {
            //         car = road.getFromA();
            //     } else {
            //         car = road.getFromB(); 
            //     }

            //     car.setFrom(dir);
            //     setCarDirection(car);
            //     
            //     if (car.getMovement() != Car.Movement.Right) {
            //         if (car.isFrom() == Direction.North || car.isFrom() == Direction.South) {
            //             if (trafficSignal.NorthSouth()) {
            //                 boolean isMatched = false;
            //                 for (Car ref : intersection) {
            //                     isMatched = car.isGoing() == ref.isGoing();
            //                     if (isMatched) break;
            //                 }

            //                 if (!isMatched) {
            //                     intersection.add(car);
            //                     road.removeTopA();
            //                 }
            //             }
            //         }
            //     } else {
            //         boolean isMatched = false;
            //         for (Car ref : intersection) {
            //             isMatched = car.isGoing() == ref.isGoing();
            //             if (isMatched) break;
            //         }

            //         if (!isMatched) {
            //             intersection.add(car);
            //             road.removeTopA();
            //         }
            //     }
            // }
        }

        ArrayList<Integer> thing = new ArrayList<Integer>();
        for (Direction key : roads.keySet()) {
            thing.add(roads.get(key).totalCurrentSize());
        }

        // System.out.println("" + clock + ": " + thing);
    }

    public boolean isLegalMove(Car car, Car ref) {
        Direction cdir = car.isGoing();
        Direction rdir = ref.isGoing();

        // TODO: make collision detection more comprehensive
        return cdir != rdir;
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

    public ArrayList<Arrivals> getAllArrivals() {
        ArrayList<Arrivals> ret = new ArrayList<Arrivals>();

        for (Direction key : roads.keySet()) {
            Road road = roads.get(key);
            if (road.getArrival() != null) ret.add(road.getArrival());
        }

        return ret;
    }

    public ArrayList<Terminals> getAllTerminals() {
        ArrayList<Terminals> ret = new ArrayList<Terminals>();

        for (Direction key : roads.keySet()) {
            Road road = roads.get(key);
            if (road.getTerminal() != null) ret.add(road.getTerminal());
        }

        return ret;
    }

    public RoadIntersection(int clock, int trafficInterval) {
        super(clock);

        intersection = new ArrayList<Car>();
        roads = new HashMap<Direction, Road>();
        roads.put(Direction.North, new Road(clock, ROAD_SIZE));
        roads.put(Direction.South, new Road(clock, ROAD_SIZE));
        roads.put(Direction.East, new Road(clock, ROAD_SIZE));
        roads.put(Direction.West, new Road(clock, ROAD_SIZE));

        roads.get(Direction.North).assignArrival(new Arrivals(clock, 10, 9), 0);
        roads.get(Direction.South).assignArrival(new Arrivals(clock, 10, 9), 1);
        roads.get(Direction.East).assignArrival(new Arrivals(clock, 10, 3), 0);
        roads.get(Direction.North).assignArrival(new Arrivals(clock, 10, 9), 1);

        roads.get(Direction.North).assignTerminal(clock, 1.0/5, 1);
        roads.get(Direction.South).assignTerminal(clock, 1.0/5, 0);
        roads.get(Direction.East).assignTerminal(clock, 1.0/5, 1);
        roads.get(Direction.West).assignTerminal(clock, 1.0/5, 0);

        trafficSignal = new TrafficSignal(clock, trafficInterval);
    }
}