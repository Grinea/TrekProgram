package main.model;

/**
* Class representing a 2D location on a sphere
* @author Owen Frere
* @throws IllegalArgumentException (unchecked) when lat or long outside 
* of [-180,180] is provided
*/
public class GPSLoc
{
    public static final double COMPTOL = 0.00000001;
    public static final double MAXGPS = 180.0;
    public static final double MINGPS = -180.0;

    private double latitude;
    private double longitude;

    /**
     * Constructor
     * @param lat as double
     * @param lon as Double
     */
    public GPSLoc(double lat, double lon)
    {
        validateCoord(lat, lon);
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Returns latitude of 2D location
     * @return latitude of location
     */
    public double getLat()
    {
        return latitude;
    }

    /**
     * Returns longitude of 2D location
     * @return longitude of location
     */
    public double getLon()
    {
        return longitude;
    }

    /**
     * Updates the location indicated
     * @param lat as double
     * @param lon as double
     */
    public void updatePosition(double lat, double lon)
    {
        validateCoord(lat, lon);
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Returns a String describing the state of the trek object
     * @return description of object's state
     */
    public String toString()
    {
        return "2D point (" + latitude + "," + longitude + ")";
    }

    /**
     * Checks an object for equality with the point
     * @param object as Object
     * @return boolean equality result
     */
    public boolean equals(Object obj)
    {
        GPSLoc tempGPS = null;

        if (obj instanceof GPSLoc)
        {
            tempGPS = (GPSLoc)obj;
            if (Math.abs(latitude - tempGPS.getLat()) < COMPTOL)
            {
                if (Math.abs(longitude - tempGPS.getLon()) < COMPTOL)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks to see if coordinates are in valid range
     * @param lat as double
     * @param lon as double
     * @throws IllegalArgumentException (un-checked) on invalid coordinates
     */
    private void validateCoord(double lat, double lon)
    {
        if ((lat <= MINGPS || lat >= MAXGPS) ||
            (lon <= MINGPS || lon >= MAXGPS))
        {
            throw new IllegalArgumentException("Invalid GPS Coordinates");
        }
    }

}

