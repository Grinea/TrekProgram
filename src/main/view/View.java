package main.view;

import java.util.Map;
import main.model.CalculatedTrek;
import main.model.Route;
import main.model.Waypoint;

/**
 * Abstract super class for views used by the Controller
 * 
 * @author Owen Frere
 */
public abstract class View
{

    /**
     * Displays an error message on the view
     * @param err as String
     */
    public void displayError(String err)
    {
        System.out.println(err);
    }

    /**
     * Displayes the associated view
     */
    public abstract void displayView(CalculatedTrek cTrek, Route detailRoute,
                                     Map<String, Route> rteMap, Waypoint lastKnown);

    /**
     * Updates the CalculatedTrek in the view and redisplay
     * @param cTrek as CalculatedTrek
     */
    public void updateCTrek(Waypoint lastKnown, CalculatedTrek cTrek)
    {
    }

    /**
     * Updates the Route List in the view and redisplay
     * @param rteMap as Map<String, Route>
     */
    public void updateRteMap(Map<String, Route> rteMap)
    {
    }

    /**
     * Informs user of completed trek
    */
    public void trekFinsihed()
    {
    }
}

