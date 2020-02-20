package main.model;

/**
* Factory for creation of 2D and 3D location objects
* @author Owen Frere
*/
public class LocationFactory
{
    /**
     * Constructor
     */
    public LocationFactory()
    {
    }
    
    /**
     * Returns a Waypoint 3D location object
     * @param lat as double
     * @param lon as double
     * @param alt as double
     * @return representation of location
     */
    public Waypoint create(double lat, double lon, double alt)
    {
        Waypoint wp = new Waypoint(create(lat, lon), alt);

        return wp;
    }

    /**
     * Returns a GPSLoc 2D location object
     * @param lat as double
     * @param lon as double
     * @return representation of location
     */
    public GPSLoc create(double lat, double lon)
    {
        GPSLoc loc = new GPSLoc(lat, lon);

        return loc;
    }
}
