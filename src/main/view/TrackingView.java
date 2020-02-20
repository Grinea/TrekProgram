package main.view;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import main.control.Controller;
import main.control.GeoUtils;
import main.control.TrackingState;
import main.model.*;

/**
* View for the Tracking controller state
* 
* @author Owen Frere
*/
public class TrackingView extends View
{
    Controller con;
    GeoUtils geoUtil;

    CalculatedTrek cTrek;
    Waypoint lastKnown;
    Route route;
    Map<String, Route> rteMap;

    /**
     * Constructor
     * @param con as Controller
     */
    public TrackingView(Controller con, GeoUtils geoUtil)
    {
        this.con = con;
        this.geoUtil = geoUtil;
    }

    /**
     * Displays information about the device's location and information
     * about the remaining trek
     * @param cTrek as CalculatedTrek
     * @param detailRoute as Route
     * @param rteMap as Map<String, Route>
     * @param lastKnown as Waypoint
     */
    @Override
    public void displayView(CalculatedTrek cTrek, Route detailRoute, 
        Map<String, Route> rteMap, Waypoint lastKnown)
    {
        DecimalFormat mDf = Settings.getInstance().getMFormat();
        DecimalFormat cDf = Settings.getInstance().getCFormat();

        this.cTrek = cTrek;
        this.lastKnown = lastKnown;

        //clear screen
        System.out.println("\033[2J\033[H");

        //Print device location
        try
        {
            System.out.println("Trekker: (" + cDf.format(lastKnown.getLat())
                + ", " + cDf.format(lastKnown.getLon())
                + ", " + mDf.format(lastKnown.getAlt()) + ")\n");
        }
        catch (NullPointerException e)
        {
            System.out.println("Device location not known");
        }

        //print trek information
        try
        {
            Waypoint nextWP = cTrek.getNextWP();
            double distanceLeft = cTrek.getDistanceLeft();
            double climbLeft = cTrek.getClimbLeft();
            double descentLeft = cTrek.getDescentLeft();
            double altToNextWP = 0;

            //add distances from lastKnown to next wp
            try
            {
                distanceLeft += geoUtil.calcMetresDistance(
                    lastKnown.getLat(), lastKnown.getLon(), 
                    nextWP.getLat(), nextWP.getLon());
                altToNextWP = nextWP.getAlt() - lastKnown.getAlt();
                if (altToNextWP < 0)
                {
                    descentLeft += Math.abs(altToNextWP);
                }
                else
                {
                    climbLeft += altToNextWP;
                }
            }
            catch (NullPointerException e3)
            {
                System.out.println("Distances do not include from "
                    + "device to next waypoint.");
            }

            String outputStr = cTrek.getName() + ": " + cTrek.getDesc()
                + "\n" + mDf.format(distanceLeft) + "m left (" + 
                mDf.format(climbLeft) + "/" + mDf.format(descentLeft) + 
                ")\n";
            
            if (cTrek.getSegments().size() > 0)
            {
                outputStr = outputStr + "Next path starts at: (" + 
                cDf.format(nextWP.getLat()) + ", " +  cDf.format(
                nextWP.getLon()) + ", " + mDf.format(nextWP.getAlt()) +
                ") and is: " + cTrek.getSegments().get(0).getDesc();
            }
            else
            {
                outputStr = outputStr + "Next waypoint is your " +
                    "destination.";
            }

            System.out.println(outputStr);
        }
        catch (NullPointerException e2)
        {
            displayError("Route was not processed correctly. Please try"
                + " again");
        }

        demoMenu();
    }

    /**
     * Updates the CalculatedTrek and location in the view and redisplay
     * @param cTrek as CalculatedTrek
     */
    public void updateCTrek(Waypoint lastKnown, CalculatedTrek cTrek)
    {
        displayView(cTrek, route, rteMap, lastKnown);
    }

    /**
     * Announces completion of route
     */
    public void trekFinsihed()
    {
        System.out.println("\033[2J\033[HTrek Completed. Congratulations!");
        try
        {
            TimeUnit.SECONDS.sleep(10);
            con.setState("routeMenu");
        }
        catch (InterruptedException e)
        {
            con.setState("routeMenu");
        }
        
    }

    /**
     * Button that the user can activate to manually indicate they have
     * reached the next Waypoint. Updates device location to next waypoint
     */
    public void manualProgress()
    {
        try
        {
            Waypoint wp = cTrek.getNextWP();
            TrackingState st = (TrackingState)(con.getCurrState());
            st.gpsChanged(wp.getLat(), wp.getLon(), wp.getAlt());
        }
        catch (NullPointerException e)
        {
            displayError("No trek to progress on");
        }
    }

    /**
     * Button to return to menu
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
        double lat,lon,alt;
        
        if (bin.equals("1"))
        {
            manualProgress();
        }
        else if (bin.equals("2"))
        {
            
            System.out.println("Enter 3 doubles on " + 
            "seperate lines");
            lat = getDub();
            lon = getDub();
            alt = getDub();
        }
        else if (bin.equals("3"))
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

        System.out.println("\n\nDemo Menu\n1 - Manual Progres\n"
            + "2 - Fake GPS Update\n3 - Back Button");

        bin = sc.next();

        return bin;
    }

    public double getDub()
    {
        Scanner sc = new Scanner(System.in);
        
        return sc.nextDouble();
    }


}