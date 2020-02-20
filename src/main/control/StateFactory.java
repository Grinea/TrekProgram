package main.control;

import java.util.Map;
import main.view.View;

/**
 * Factory for the creation of controller states
 * 
 * @author Owen Frere
 * @throws StateLoadException (Unchecked) if error is encountered during load
 */

public class StateFactory
{
    Map<String, View> views;

    /**
     * Constructor
     * @param views as Map<String, View>
     */
    public StateFactory(Map<String, View> views)
    {
        this.views = views;
    }

    /**
     * Returns a State created for the string name
     * @param con as Controller
     * @param stateName as String
     * @return constructed state
     * @throws StateLoadException (Unchecked) if error occurs during load
     */
    public State create(Controller con, String stateName)
    {
        if (con == null || views == null)
        {
            throw new StateLoadException("Invalid controller or view");
        }

        return create(stateName, con, views.get(stateName));
    }

    /**
     * Creates a State for the controller
     * @param s as String
     * @param con as Controller
     * @param view as View
     * @return a loaded controller state
     * @throws StateLoadException (Unchecked) if error occurs during creation
     */
    private State create(String s, Controller con, View view)
    {
        switch (s)
        {
            case "routeMenu":
                return new RouteMenuState(con, view);
            case "detailList":
                return new DetailListState(con, view);
            case "tracking":
                return new TrackingState(con, view);
            default:
                throw new StateLoadException("Invalid state "
                    + "attempted construction");
        }
    }
}