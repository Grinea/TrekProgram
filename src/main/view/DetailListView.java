package main.view;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

import main.control.Controller;
import main.model.*;

/**
* View for the DetailList controller state
* 
* @author Owen Frere
*/
public class DetailListView extends View
{
    private Controller con;
    private Route route;
    private CalculatedTrek cTrek;
    private Map<String, Route> rteMap;
    private Waypoint lastKnown;

    /**
     * Constructor
     * @param con program's controller
     */
    public DetailListView(Controller con)
    {
        this.con = con;
    }

    /**
     * Displays the in depth details of the provided route
     * @param cTrek as CalculatedTrek
     * @param detailRoute as Route
     * @param rteMap as Map<String, Route>
     * @param lastKnown as Waypoint
     */
    public void displayView(CalculatedTrek cTrek, Route detailRoute, 
        Map<String, Route> rteMap, Waypoint lastKnown)
    {
        DecimalFormat mDf = Settings.getInstance().getMFormat();
        DecimalFormat cDf = Settings.getInstance().getCFormat();

        this.route = detailRoute;

        //clear screen
        System.out.println("\033[2J\033[H");

        try
        {
            System.out.println("--- Route Details ---\n" + route.getName() +
                ": " + route.getDesc() + ".\nStart(Lat,Lon,Alt)"
                + " description End(Lat, Lon, Alt)\n"); 

            for (Segment s : detailRoute.getSegments())
            {
                System.out.println("(" + cDf.format(s.getStart().getLat())
                + "," + cDf.format(s.getStart().getLon())
                + "," + mDf.format(s.getStart().getAlt()) + ")"
                + " " + s.getDesc() + " ("
                + cDf.format(s.getEnd().getLat())
                + "," + cDf.format(s.getEnd().getLon())
                + "," + mDf.format(s.getEnd().getAlt()) + ")"
                );
            }
        }
        //if route supplied is null
        catch (NullPointerException e)
        {        
            displayError("Unable to read route information.");
        }

        demoMenu();
    }

    /**
     * Button has been clicked to indicate the user wants to start
     * tracking the current displayed route
     */
    public void startRoute()
    {
        con.setState("tracking");
    }

    /**
     * Button to return to the menu list
     */
    public void backButton()
    {
        con.setState("routeMenu");
    }

    /**
     * demo menu
     */
    public void demoMenu()
    {
        String bin = getStr();
        
        if (bin.equals("1"))
        {
            startRoute();
        }
        else if (bin.equals("2"))
        {
            backButton();
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

        System.out.println("\n\nDemo Menu\n1 - Begin Route\n2 - Back " +
            "Button");

        bin = sc.next();

        return bin;
    }

}