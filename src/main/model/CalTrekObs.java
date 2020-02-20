package main.model;

/**
* Interface for observers for the CalculatedTrek
* 
* @author Owen Frere
*/
public interface CalTrekObs
{
    /**
     * Observer method for waypoint being reached
     */
    public void waypointReached();

    /**
     * Observer method for end of trek being reached
     */
    public void endOfTrek();
}