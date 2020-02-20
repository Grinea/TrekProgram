package main.model;

import java.util.LinkedList;
import java.util.List;

/**
* Represents a straight line path between two 3D points on a sphere
* 
* @author Owen Frere
* @throws IllegalArgumentException (unchecked) if a negative length or
* null objects are supplied
*/
public class Segment extends TrekPart
{
    private Waypoint start;
    private Waypoint end;
    private double length;
    private double altChange;
    private String desc;

    /**
     * Constructor
     * @param start as Waypoint
     * @param end as Waypoint
     * @param length as double
     * @param altChange as double
     * @param desc as String
     * @throws IllegalArgumentException (unchecked) if a negative length or
     * null objects are supplied
     */
    public Segment(Waypoint start, Waypoint end, double length,
                   double altChange, String desc)
    {
        if (start == null ||
            end == null || 
            desc == null ||
            length < 0)
        {
            throw new IllegalArgumentException("Invalid Segment properties");
        }

        this.start = start;
        this.end = end;
        this.length = length;
        this.altChange = altChange;
        this.desc = desc;
    }

   
    /**
     * Returns Waypoint representing start of path
     * @return start of path
     */
    public Waypoint getStart()
    {
        return start;
    }

    /**
     * Returns Waypoint representing end of path
     * @return end of path
     */
    public Waypoint getEnd()
    {
        return end;
    }

    /**
     * Returns length of path as double
     * @return
     */
    public double getLength()
    {
        return length;
    }

    /**
     * Returns List<Segment> representing the path
     * @return segement in a list
     */
    public List<Segment> getSegments()
    {
        LinkedList<Segment> segs = new LinkedList<Segment>();
        segs.add(this);

        return segs;
    }

    /**
     * Returns String description of path
     * @return description of path
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Returns altitude gained along path as double
     * @return altitude gain
     */
    public double getAltIncrease()
    {
        if (altChange > 0)
        {
            return altChange;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns altitude lost along path as double
     * @return altitude loss
     */
    public double getAltDecrease()
    {
        if (altChange < 0)
        {
            return Math.abs(altChange);
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns altitude change along path as double
     * @return altitude change
     */
    public double getAltChange()
    {
        return altChange;
    }

    /**
     * Returns a String describing the state of the trek object
     * @return description of object's state
     */
    public String toString()
    {
        return "Path starting at " + start.toString() + " and ending at " 
                + end.toString() + "\nLength: " + length + "m, Altitude" +
                " change: " + altChange + "m\nDescription: " + desc;
    }

    /**
     * Checks an object for equality with the segment
     * @param object as Object
     * @return boolean equality result
     */
    public boolean equals(Object obj)
    {
        Segment tempSeg = null;

        if (obj instanceof Segment)
        {
            tempSeg = (Segment)obj;
            if (start.equals(tempSeg.getStart()))
            {
                if (end.equals(tempSeg.getEnd()))
                {
                    if (desc.equals(tempSeg.getDesc()))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
