
/* CS4632 - Modeling and Simulation
 * Section 01
 * Final Project
 * March 02, 2020
 * Christian Byrne and Patrick Sweeney
 */

public abstract class SimObject {
    private int creationTime;

    public int getCreationTime() { return creationTime; }
    public SimObject(int clock) { creationTime = clock; }
}