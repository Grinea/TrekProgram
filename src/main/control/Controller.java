package main.control;

import java.util.Map;
import java.io.IOException;
import main.model.*;

/**
* Main controller for the trek tracking program
* 
* @author Owen Frere
*/
public class Controller implements LocatorObs
{

    //Factories
    private LocationFactory lFac;
    private TrekFactory tFac;
    private StateFactory sFac;

    //State
    private State currState;

    //Data fields
    private Map<String, Route> routes;
    private Waypoint lastKnown;
    private Route detailRoute;

    //Tool fields
    private GeoUtils geoUtil;
    private TrekLocator locatorLink;

    /**
     * Creates controller and initialises link to gps locator
     */
    public Controller(GeoUtils geoUtil, LocationFactory lFac, 
        TrekFactory tFac, TrekLocator locatorLink)
    {
        this.geoUtil = geoUtil;
        this.lFac = lFac;
        this.tFac = tFac;
        this.locatorLink = locatorLink;
        this.locatorLink.registerObs(this);
    }

    /**
     * Returns the Map<String, Route> of routes known by the controller
     * @return collection known routes
     */
    public Map<String, Route> getRoutes()
    {
        return routes;
    }

    /**
     * Set the StateFactory for the controller
     * @param sFac as StateFactory
     */
    public void setStateFactory(StateFactory sFac)
    {
        this.sFac = sFac;
    }

    /**
     * Returns a refernce to the controllers TrekFactory
     * @return the trekFactory reference
     */
    public TrekFactory getTFac() 
    {
        return tFac;
    }

    /**
     * Returns a refernce to the controllers LocationFactory
     * @return the trekFactory reference
     */
    public LocationFactory getLFac() 
    {
        return lFac;
    }

    /**
     * Returns a Waypoint of the last known location of the phone
     * @return last known location
     */
    public Waypoint getLastKnown()
    {
        return lastKnown;
    }

    /**
     * Returns Route that was last displayed in detail list
     * @return route most recently shown in full
     */
    public Route getDetailRoute()
    {
        return detailRoute;
    }
    
    /**
     * Returns the State that the controller is currently operating in
     * @return current state of controller
     */
    public State getCurrState()
    {
        return currState;
    }

    /**
     * Returns the Controllers GeoUtils
     * @return controller's geoUtil
     */
    public GeoUtils getGeoUtil()
    {
        return geoUtil;
    }

    /**
     * Returns a reference to the gps hardware link
     * @return controller's gps hardware link
     */
    public TrekLocator getLocatorLink()
    {
        return locatorLink;
    }

    /***
     * Begins controller operation by setting current state and displaying it
     * @throws StateLoadException if initialisation fails
     */
    public void run()
        throws StateLoadException
    {
        currState = sFac.create(this, "routeMenu");
        currState.initialise();
    }
    
    /**
     * Observer method for the LocatorObs observer. Creates or updates 
     * last known location, and registers Observers to it in the case
     * of creation. Also calls update location on current state.
     * @param lat new latitude as double
     * @param lon new longitude as double
     * @param alt new altitude as double
     * 
     * Note: This is used to make sure the program records any location
     * it receives between initialising and switching to tracking mode
     * to increase the chances of having at least a rough starting location
     * when entering tracking mode, not actually necessary for functioning
     */
    public void gpsChanged(double lat, double lon, double alt)
    {
        lastKnown = lFac.create(lat, lon, alt);
    }

    /**
     * Changes state and displays related view, on fail returns to menu
     * @param newState as String
     */
    public void setState(String newState)
    {
        try
        {
            currState.deconstruct();
            currState = sFac.create(this, newState);
            currState.initialise();
        }
        catch (StateLoadException e)
        {
            currState = sFac.create(this, "routeMenu");
            currState.initialise();
        }
    }

    /**
     * Sets the route which will be displayed in detialed view
     * @param route as Route
     */
    public void setDetailRoute(Route route)
    {
        detailRoute = route;
    }

    /**
     * Requests route data from remote source and converts to required
     * object form
     */
    public Map<String, Route> loadRouteData()
    {
        try
        {
            routes = tFac.mapCreate(geoUtil.retrieveRouteData());
        }
        catch (IOException e)
        {
            currState.displayError("Unable to retrieve data from server");
        }
        catch (TrekPartException e2)
        {
            currState.displayError("Unable to process data from server");
        }

        return routes;
    }

}