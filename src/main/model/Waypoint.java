package main.model;

/**
 * Class representing a 3D location on a sphere by latitude, longitude, 
 * and altitude. Latitude and longitude are restricted to range [-180,180]
 * but altitude is unrestricted
 * 
 * @author Owen Frere
 * @throws IllegalArgumentException (unchecked) for null imports or invalid
 * coordinates
 */
public class Waypoint
{
    //float comparison tolerance
    public static final double COMPTOL = 0.00000001;

    private GPSLoc gpsLoc;
    private double altitude;

    /**
     * Constructor
     * @param gpsLoc as GPSLoc
     * @param altitude as double
     */
    public Waypoint(GPSLoc gpsLoc, double altitude)
    {
        if (gpsLoc == null)
        {
            throw new IllegalArgumentException("Null GPSLoc");
        }

        this.gpsLoc = gpsLoc;
        this.altitude = altitude;
    }

    /**
     * Returns 2D location represented by a GPSLoc
     * @return 2D location represented
     */
    public GPSLoc getGPSLoc()
    {
        return gpsLoc;
    }

    /**
     * Returns the latitude of the location as a double
     * @return latitude of location
     */
    public double getLat()
    {
        return gpsLoc.getLat();
    }    

    /**
     * Returns the longitude of the location as a double
     * @return longitude of location
     */
    public double getLon()
    {
        return gpsLoc.getLon();
    }

    /**
     * Returns the altitude of the location as a double
     * @return altitude of location
     */
    public double getAlt()
    {
        return altitude;
    }

    /**
     * Returns a String describing the state of the trek object
     * @return description of object's state
     */
    public String toString()
    {
        return "3D point, " + altitude + " metres about sea level at " 
                + gpsLoc.toString();
    }

    /**
     * Checks an object for equality with the Waypoint
     * @param object as Object
     * @return boolean equality result
     */
    public boolean equals(Object obj)
    {
        Waypoint tempWP = null;

        if (obj instanceof GPSLoc)
        {
            tempWP = (Waypoint)obj;
            if (Math.abs(altitude - tempWP.getAlt()) < COMPTOL)
            {
                if (gpsLoc.equals(tempWP.getGPSLoc()))
                {
                    return true;
                }
            }
        }

        return false;
    }

}
