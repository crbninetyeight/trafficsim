
public abstract class SimObject {
    private int creationTime;

    public abstract void tick(int clock);

    public getCreationTime() { return creationTime; }
    public SimObject(int clock) { creationTime = clock; }
}