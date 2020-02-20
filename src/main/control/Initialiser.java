package main.control;

import java.util.Map;

import main.model.LocationFactory;
import main.model.TrekFactory;
import main.model.TrekLocator;
import main.view.View;
import main.view.ViewFactory;
import main.view.ViewLoadException;

/**
 * Initialises the controller and views for the trek tracking program
 * Will enter a new mode if the first command line parameter is demoMode.
 * This is /not/ intended to be in the program but purely for the
 * demontration without a full UI.
 * 
 * @param args as String[]
 */
public class Initialiser
{
    public static void main(String[] args)
    {
        String[] requiredViews = {"routeMenu","detailList","tracking"};
        GeoUtils geoUtil = new GeoUtils();
        ViewFactory vFac = new ViewFactory(geoUtil);
        LocationFactory lFac = new LocationFactory();
        TrekLocator locatorLink = new TrekLocator();
        TrekFactory tFac = new TrekFactory(geoUtil, lFac);
        StateFactory sFac;
        Map<String, View> views = null;
        Controller con = null;

        try
        {
            con = new Controller(geoUtil, lFac, tFac, locatorLink);

            views = vFac.loadViews(con, requiredViews);
            sFac = new StateFactory(views);
            
            con.setStateFactory(sFac);
            con.run();
        }
        catch (ViewLoadException | StateLoadException e)
        {
            System.out.println("System initialisation failed.");
            System.out.println(e.getMessage());
        }
    }
}
