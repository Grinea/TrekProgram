package main.model;

import java.util.List;

/**
 * Abstract superclass for classes that can be used to represent a path
 * between 2 points of a sphere
 * 
 * @author Owen Frere
 */
public abstract class TrekPart
{
    public abstract Waypoint getStart();
    public abstract Waypoint getEnd();
    public abstract double getLength();
    public abstract List<Segment> getSegments();
    public abstract String getDesc();
    public abstract double getAltIncrease();
    public abstract double getAltDecrease();
    public abstract boolean equals(Object obj);
}
