package main.control;

import main.model.*;
import main.view.View;

/**
* Controller state for tracking display
* 
* @author Owen Frere
*/
public class TrackingState extends State implements LocatorObs
{    

    Controller con;
    View view;
    CalculatedTrek cTrek;
    Waypoint lastKnown, approaching;
    GeoUtils geoUtil;

    /**
     * Constructor
     * @param con as Controller
     * @param view as View
     */
    public TrackingState(Controller con, View view)
    {
        this.con = con;
        this.view = view;
        this.geoUtil = con.getGeoUtil();
        con.getLocatorLink().registerObs(this);
    }

    /**
     * Displays current location of device and information about
     * remaining trek on the associated view
     */
    @Override
    public void initialise()
    {  
        try
        {
            cTrek = con.getTFac().create(con.getDetailRoute());
            this.approaching = cTrek.getNextWP();
            this.lastKnown = con.getLastKnown();
            view.displayView(cTrek, con.getDetailRoute(), con.getRoutes(), 
                lastKnown);
        }
        catch (TrekCalculationException e)
        {
            view.displayError("Unable to calculate Trek");
        }
    }

    @Override
    public void deconstruct()
    {
        con.getLocatorLink().removeObs(this);
    }
    
    /**
     * Displays error on the associated view
     * @param err as String
     */
    @Override
    public void displayError(String err)
    {
        view.displayError(err);
    }

    /**
     * Updates the last known location of the device
     * @param lat as double
     * @param lon as double
     * @param alt as double
     */
    public void gpsChanged(double lat, double lon, double alt)
    {
        lastKnown = con.getLFac().create(lat, lon, alt);
        
        if (closeEnough(lastKnown, cTrek.getNextWP()))
        {
            if (cTrek.getSegments().size() == 0)
            {
                view.trekFinsihed();
            }
            else
            {
                cTrek.reachedNext();
                view.updateCTrek(lastKnown, cTrek);
            }
        }
        else
        {
            view.updateCTrek(lastKnown, cTrek);
        }
    }

    /**
     * Checks if two Waypoints are close enough to be considered the same
     * by the program
     * @param wp1 as Waypoint
     * @param wp2 as Waypoint
     * @return are they close enough
     */
    public boolean closeEnough(Waypoint wp1, Waypoint wp2)
    {
        Settings settings = Settings.getInstance();

        if (Math.abs(wp1.getAlt() - wp2.getAlt()) < settings.getVLimit())
        {
            if (settings.getHLimit() > geoUtil.calcMetresDistance(
                wp1.getLat(), wp2.getLon(),
                wp2.getLat(), wp2.getLon()))
            {
                return true;
            }
        }
        return false;
    }
}