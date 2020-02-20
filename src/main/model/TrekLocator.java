package main.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Stub of template class that connects to gps hardware for location
 * updates. Hook method converted into an observer pattern to allow
 * additional parts of program to observer gps updates.
 * 
 * @author Owen Frere
 */
public class TrekLocator extends GpsLocator
{
    private Set<LocatorObs> obs;
    
    /**
     * Constructor
     */
    public TrekLocator()
    {
        this.obs = new HashSet<LocatorObs>();
    }

    /**
     * Notifies its observers and provides lat,lon,alt when gps hardware
     * recieves location
     * @param latitude as double
     * @param longitude as double
     * @param altitude as double
     */
    @Override
    protected void locationReceived(double latitude,
        double longitude, double altitude)
    {
        for (LocatorObs o : obs)
        {
            o.gpsChanged(latitude, longitude, altitude);
        }
    }

    /**
     * Registers an observer to be notified when location received
     * @param observer as LocatorObs
     */
    public void registerObs(LocatorObs observer)
    {
        obs.add(observer);
    }

    /**
     * Removes an observer to be notified when location received
     * @param observer as LocatorObs
     */
    public void removeObs(LocatorObs observer)
    {
        obs.remove(observer);
    }
}