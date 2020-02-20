package main.control;

import java.io.IOException;

/**
* Stub class for testing and demonstration. Will represent location
* calculation tools and route data retrieval methods
* 
* @author Owen Frere
* @throws IOException when data retrieval from remote location fails
*/
public class GeoUtils
{
    private String src;
    private double circumference = 6371000.0;
    
    public GeoUtils()
    {
        src = "theClimb Amazing views!\n-31.94,115.75,47.1,Easy start\n"
            + "-31.94,115.75,55.3,Tricky, watch for drop bears.\n-31.94,"
            + "115.75,71.0,I*feel,like.over-punctuating!@#$%^&*()[]{}<>."
            + "?_+\n-31.93,115.75,108.0,Getting there\n-31.93,115.75,131"
            + ".9\nmainRoute Since I was young\n-31.96,115.80,63.0,I kne"
            + "w\n-31.95,115.78,45.3,I'd find you\n-31.95,115.77,44.8,*t"
            + "heStroll\n-31.94,115.75,47.1,But our love\n-31.93,115.72,"
            + "40.1,Was a song\n-31.94,115.75,47.1,*theClimb\n-31.93,115"
            + ".75,131.9,Sung by a dying swan\n-31.92,115.74,128.1\ntheS"
            + "troll Breathe in the light\n-31.95,115.77,44.8,I'll stay "
            + "here\n-31.93,115.76,43.0,In the shadow\n-31.94,115.75,47."
            + "1\n";
    }

    /**
     * Returns String containing all route data from remote location
     * @return formatted String of route data
     * @throws IOException when data retrieval fails
     */
    public String retrieveRouteData()
        throws IOException
    {
        return src;
    }

    /**
     * Sets stub output to the provided String
     * @param src stub output field
     */
    public void setSrc(String src)
    {
        this.src = src;
    }

    /**
     * Calculates the distance between two 2D points on a sphere of
     * set circumference
     * @param lat1 first point's latitude
     * @param lon1 first point's longitude
     * @param lat2 second point's latitude
     * @param lon2 second point's longitude
     * @return metres distance between points
     */
    public double calcMetresDistance(double lat1, double lon1, 
        double lat2, double lon2)
    {
        //method provided by Dr Cooper in assignment sheet
        double distance = circumference * Math.acos(
            Math.sin((Math.PI * lat1) / 180) *
            Math.sin((Math.PI * lat2) / 180) +
            Math.cos((Math.PI * lat1) / 180) *
            Math.cos((Math.PI * lat2) / 180) *
            Math.cos((Math.PI * Math.abs(lon1 - lon2))/180));

        return distance;
    }
}
