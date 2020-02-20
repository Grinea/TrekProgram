package main.model;

import java.util.*;

/**
* Represents collection of one or more directed straight line paths 
* that combine to be a path between 2 3D points on a sphere.
* 
* @author Owen Frere
* @throws IllegalArgumentException(unchecked) if null route provided
*/
public class CalculatedTrek
{
    private String name, desc;
    private List<Segment> segs;
    private double distanceLeft, climbLeft, descentLeft;
    private Set<CalTrekObs> obs;
    private Waypoint nextWP;

    /**
     * Constructor
     * @param route as Route
     */
    public CalculatedTrek(Route route)
    {
        if (route == null)
        {
            throw new IllegalArgumentException("Invalid route provided");
        }

        this.name = route.getName();
        this.desc = route.getDesc();
        this.segs = route.getSegments();
        this.distanceLeft = route.getLength();
        this.climbLeft = route.getAltIncrease();
        this.descentLeft = route.getAltDecrease();
        this.obs = new HashSet<CalTrekObs>();
        this.nextWP = segs.get(0).getStart();
    }

    /**
     * Returns a String of trek's name
     * @return name of trek
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns a String description of trek
     * @return description of trek
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Returns a List<Segment> of all the paths that make up the trek
     * @return list of the segments
     */
    public List<Segment> getSegments()
    {
        return segs;
    }

    /**
     * Returns the horizontal distance left in the trek as a double
     * @return horizontal distance remaining
     */
    public double getDistanceLeft()
    {
        return distanceLeft;
    }

    /**
     * Returns the total climb of all remaining paths as a double
     * @return total climb distance remaining
     */
    public double getClimbLeft()
    {
        return climbLeft;
    }

    /**
     * Returns the total descent of all remaining paths as a double
     * @return total descent distance remaining
     */
    public double getDescentLeft()
    {
        return descentLeft;
    }

    /**
     * Returns a Waypoint indicating the location of the next waypoint
     * in the trek
     * @return start of next path
     */
    public Waypoint getNextWP()
    {
        return nextWP;
    }

    /**
     * Updates the classfields to indicate that the next waypoint has been
     * been reached. Updates observers based on it being the final point or
     * otherwise
     */
    public void reachedNext()
    {
        distanceLeft -= segs.get(0).getLength();
        climbLeft -= segs.get(0).getAltIncrease();
        descentLeft -= segs.get(0).getAltDecrease();
        if (segs.size() > 1)
        {
            segs.remove(0);
            nextWP = segs.get(0).getStart();
        }
        else
        {
            nextWP = segs.get(0).getEnd();
            segs.remove(0);
        }
    }

    /**
     * Notifies observers that the next path has begun
     */
    public void notifyNextWaypoint()
    {
        for (CalTrekObs o : obs)
        {
            o.waypointReached();
        }
    }

    /**
     * Notifies observers that the final waypoint has been reached
     * and then removes observers from the list
     */
    public void notifyEndOfTrek()
    {
        for (CalTrekObs o : obs)
        {
            o.endOfTrek();
        }
    }

    /**
     * Registers an observer to be notified when a path is started
     * @param observer as CalTrekObs
     */
    public void registerObs(CalTrekObs observer)
    {
        obs.add(observer);
    }

    /**
     * Removes an observer from observer list
     * @param observer as CalTrekObs
     */
    public void removeObs(CalTrekObs observer)
    {
        obs.remove(observer);
    }

    /**
     * Returns a String describing the state of the trek object
     * @return description of object's state
     */
    public String toString()
    {
        return "Trek: " + name + ", " + desc + ", containing " + segs.size()
                + " segements.\nTrek has " + distanceLeft 
                + "m to go (" + climbLeft + "C/" + descentLeft + "D)";
    }

    /**
     * Checks an object for equality with the current trek
     * @param object as Object
     * @return boolean equality result
     */
    public boolean equals(Object obj)
    {
        CalculatedTrek tempTrek = null;

        if (obj instanceof CalculatedTrek)
        {
            tempTrek = (CalculatedTrek)obj;
            if (name.equals(tempTrek.getName()))
            {
                if (desc.equals(tempTrek.getDesc()))
                {
                    if (segs.equals(tempTrek.getSegments()))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}