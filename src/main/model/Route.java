package main.model;

import java.util.*;

/**
 * Represents a route between 2 3D points by a series of straight
 * line paths between 3D locations.
 * @throws IllegalArgumentException (unchecked) if null list of TrekParts
 * is provided
 */
public class Route extends TrekPart
{

    private String name;
    private String desc;
    private List<TrekPart> parts;
    
    /**
     * Constructor
     * @param name as String
     * @param desc as String
     * @param parts as List<TrekPart>
     * @throws IllegalArgumentException (unchecked) if null list of TrekParts
     * is provided
     */
    public Route(String name, String desc, List<TrekPart> parts)
    {
        if (name == null || desc == null ||
            parts == null || parts.size() == 0)
        {
            throw new IllegalArgumentException("Invalid Route properties");
        }

        this.name = name;
        this.desc = desc;
        this.parts = parts;
    }

    /**
     * Returns the Waypoint representing the start of the route
     * @return start of route
     */
    public Waypoint getStart()
    {
        return parts.get(0).getStart();
    }

    /**
     * Returns the Waypoint representing the end of the route
     * @return end of route
     */
    public Waypoint getEnd()
    {
        return parts.get(parts.size()-1).getEnd();
    }

    /**
     * Returns length of route in metres as a double
     * @return length of route
     */
    public double getLength()
    {
        double lengthTotal = 0;

        for (TrekPart tp : parts)
        {
            lengthTotal += tp.getLength();
        }

        return lengthTotal;
    }

    /**
     * Returns String describing the route
     * @return description of route
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Returns String of route name
     * @return name of route
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the List<Segment> that represents all straight
     * line paths in the route
     * @return list of paths in route
     */
    public List<Segment> getSegments()
    {
        List<Segment> segs = new LinkedList<Segment>();

        for (TrekPart tp : parts)
        {
            segs.addAll(tp.getSegments());
        }

        return segs;
    }

    /**
     * Returns the total of climb of upward segments in the 
     * route as a double
     * @return altitude increases
     */
    public double getAltIncrease()
    {
        double altIncreaseTotal = 0;

        for (TrekPart tp : parts)
        {
            altIncreaseTotal += tp.getAltIncrease();
        }

        return altIncreaseTotal;
    }

    /**
     * Returns the total of descent of downward segments in the 
     * route as a double
     * @return altitude decreases
     */
    public double getAltDecrease()
    {
        double altDecreaseTotal = 0;

        for (TrekPart tp : parts)
        {
            altDecreaseTotal += Math.abs(tp.getAltDecrease());
        }

        return altDecreaseTotal;
    }

    /**
     * Returns a String describing the state of the trek object
     * @return description of object's state
     */
    public String toString()
    {
        return "Route named: " + name + " starting at: " + 
                parts.get(0).getStart() + " and ending at: " +
                parts.get(parts.size() - 1).getEnd() + " and containing "
                + parts.size() + " TrekParts";
    }

    /**
     * Checks an object for equality with the route
     * @param object as Object
     * @return boolean equality result
     */
    public boolean equals(Object obj)
    {
        Route tempRoute = null;

        if (obj instanceof Route)
        {
            tempRoute = (Route)obj;
            if (name.equals(tempRoute.getName()))
            {
                if (desc.equals(tempRoute.getDesc()))
                {
                    if (this.getSegments().equals(tempRoute.getSegments()))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
