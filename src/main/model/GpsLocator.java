package main.model;

/**
* Stub abstract class for connecting to GPS hardware in a template pattern
* @author Owen Frere
*/
public abstract class GpsLocator
{

    /**
     * When a gps update is received this hook method is called
     * @param latitude as double
     * @param longitude as double
     * @param altitude as double
     */
    protected abstract void locationReceived(double latitude,
        double longitude, double altitude);
}