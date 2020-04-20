
public abstract class SimObject {
    private int creationTime;

    public int getCreationTime() { return creationTime; }
    public SimObject(int clock) { creationTime = clock; }
}