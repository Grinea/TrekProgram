package main.model;

/**
* Interface for observers for the TrekLocator
* 
* @author Owen Frere
*/
public interface LocatorObs
{
    /**
     * observer method
     * @param lat as double
     * @param lon as double
     * @param alt as double
     */
    public abstract void gpsChanged(double lat, double lon, double alt);
}