package main.view;

import main.control.Controller;
import main.control.GeoUtils;

import java.util.Map;
import java.util.HashMap;

/**
 * Factory class for creation of views used by Controller
 * 
 * @author Owen Frere
 */
public class ViewFactory
{

    private GeoUtils geoUtil;

    /**
     * Constructor
     * @param geoUtils as GeoUtils
     */
    public ViewFactory(GeoUtils geoUtil)
    {
        this.geoUtil = geoUtil;
    }

    /**
     * Loads a Map<String, View> of the views used by Controller keyed
     * by their related state names
     * @param con as Controller
     * @param reqViews as String[]
     * @return map of views
     * @throws ViewLoadException if load fails
     */
    public Map<String, View> loadViews(Controller con, String[] reqViews)
        throws ViewLoadException
    {
        if (con == null)
        {
            throw new ViewLoadException("Invalid controller");
        }

        Map<String, View> vMap = new HashMap<String, View>();

        for (String s : reqViews)
        {
            vMap.put(s, create(s, con));
        }
    
        return vMap;   
    }

    /**
     * Creates a View based on the name provided
     * @param s as String
     * @param con as Controller
     * @return view
     * @throws ViewLoadException if unknown name provided
     */
    private View create(String s, Controller con)
        throws ViewLoadException
    {
        switch (s)
        {
            case "routeMenu":
                return new RouteMenuView(con);
            case "detailList":
                return new DetailListView(con);
            case "tracking":
                return new TrackingView(con, geoUtil);
            default:
                throw new ViewLoadException("Invalid state " +
                    " attempted construction");
        }
    }
}