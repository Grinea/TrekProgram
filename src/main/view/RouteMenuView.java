package main.view;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

import main.control.Controller;
import main.model.*;

/**
* View for the RouteMenu controller state
* 
* @author Owen Frere
*/
public class RouteMenuView extends View
{
    private Controller con;
    private Route route;
    private CalculatedTrek cTrek;
    private Map<String, Route> rteMap;
    private Waypoint lastKnown;

    /**
     * Constructor
     * @param con as Controller
     */
    public RouteMenuView(Controller con)
    {
        this.con = con;
    }

    /**
     * Displays the in list of known routes
     * @param cTrek as CalculatedTrek
     * @param detailRoute as Route
     * @param rteMap as Map<String, Route>
     * @param lastKnown as Waypoint
     */
    @Override
    public void displayView(CalculatedTrek cTrek, Route detailRoute, 
        Map<String, Route> rteMap, Waypoint lastKnown)
    {
        DecimalFormat df = Settings.getInstance().getMFormat();

        this.cTrek = cTrek;
        this.route = detailRoute;
        this.rteMap = rteMap;
        this.lastKnown = lastKnown;

        //clear screen
        System.out.println("\033[2J\033[H");

        System.out.println("--- Known Routes ---\n Name: Description. " +
            "Length (Climb/Descent)\n");

        try
        {
            if (rteMap.size() == 0)
            {
                System.out.println("No valid routes in list. Please try "
                    + "loading again.");
            }
            else
            {
                for (Route rte : rteMap.values())
                {
                    System.out.println(rte.getName() + ": " + rte.getDesc() +
                        ". Length of " + df.format(rte.getLength()) + "m (" + 
                        df.format(rte.getAltIncrease()) + "m/" + 
                        df.format(Math.abs(rte.getAltDecrease())) + "m)");
                }
            }
        }
        catch (NullPointerException e)
        {
            displayError("No known routes. Please load route data.");
        }

        demoMenu();
    }

    /**
     * Updates the Route List in the view and redisplay
     */
    public void updateRteMap(Map<String, Route> rteMap)
    {
        displayView(cTrek, route, rteMap, lastKnown);
    }

    /**
     * Button to initiates a data load by the controller
     */
    public void loadRouteData()
    {
        rteMap = con.loadRouteData();
        updateRteMap(rteMap);
    }

    /**
     * Selects a route to view in detail and updates controller state
     * @param route as Route
     */
    public void goToDetailList(Route route)
    {
        con.setDetailRoute(route);
        con.setState("detailList");
    }

    /**
     * demo menu
     */
    public void demoMenu()
    {
        String bin = getStr();
        
        if (bin.equals("1"))
        {
            loadRouteData();
        }
        else if (con.getRoutes().containsKey(bin))
        {
            goToDetailList(con.getRoutes().get(bin));
        }
        else
        {
            displayView(cTrek, route, rteMap, lastKnown);
        }

    }

    public String getStr()
    {
        Scanner sc = new Scanner(System.in);
        String bin;

        System.out.println("\n\nDemo Menu\n1 - Load Route Data\nRoute " +
            "name - show details");

        bin = sc.next();

        return bin;
    }
}