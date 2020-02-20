package main.model;

import main.control.GeoUtils;

import java.util.*;
/**
 * Factory class for the creation of TrekParts. As TrekParts can be
 * recursively loaded into each other the factory finishes all TrekParts
 * from the same context before returning any
 * 
 * @author Owen Frere
 * @throws TrekPartException if failure occurs in route creation
 * @throws TrekCalculationException if failure occurs in route flattening
 */
public class TrekFactory
{
    private Set<String> invSet;
    private Map<String, String> strMap;
    private Map<String, Route> rteMap;
    private LocationFactory locFac;
    private GeoUtils geoUtil;

    /**
     * Constructor
     */
    public TrekFactory(GeoUtils geoUtil, LocationFactory lFac)
    {
        invSet = new HashSet<String>();
        strMap = new HashMap<String, String>();
        rteMap = new HashMap<String, Route>();
        locFac = lFac;
        this.geoUtil = geoUtil;
    }

    /**
     * Converts a string of all Routes from a context into a 
     * Map<String, Route> of routes keyed by their names
     * @param str as String
     * @return map of routes
     * @throws TrekPartException
     */
    public Map<String,Route> mapCreate(String str)
        throws TrekPartException
    {
        if (str == null)
        {
            throw new TrekPartException("Cannot read route data");
        }
        loadStrMap(str);

        for (String mapStr : strMap.values()) 
        {
            try
            {
                createRoute(mapStr);
            }
            catch (IllegalArgumentException e)
            {
                /*
                * Log count/names of bad routes from invSet.
                * No other error handling here as it occurs in recursive
                * snap back.
                */
            }
        }
        return rteMap;
    }

    /**
     * Converts route data String into a Map<String, String> of component
     * route Strings keyed by the route names
     * @param str as String
     */
    private void loadStrMap(String str)
    {
        int nameEnd = 0;
        int ii = 0;
        String strName = null;
        String strData = null;
        String[] srcArr = str.split("\n");
    
        //while exists new lines
        while (ii < srcArr.length)
        {
            srcArr[ii] = srcArr[ii].trim();
            //if trimmed string isn't empty
            if (srcArr[ii].length() > 0)
            {
                //if name is not set
                if (strName == null)
                {
                    //extract name and seperate out description
                    nameEnd = srcArr[ii].indexOf(' ');
                    strName = srcArr[ii].substring(0,nameEnd);
                    strData = strName + "\n";
                    if (0 < nameEnd)
                    {
                        strData = strData + srcArr[ii].substring(
                            nameEnd+1, srcArr[ii].length()) + "\n";
                    }
                    ii++;
                }
                //if next line is a valid name
                else if (srcArr[ii].matches("^[A-Za-z0-9_]+\\s.+"))
                {
                    //end string and insert to map
                    strMap.put(strName, strData);
                    strName = null;
                    strData = new String();
                }
                //line is a data line
                else
                {
                    strData = strData + srcArr[ii] + "\n";
                    ii++;
                }
            }
        }

        //add final string to map
        if (strName.length() > 0 && strData.length() > 0)
        {
            strMap.put(strName, strData);
        }
    }

    /**
     * Converts a route String to its representative Route and inserts
     * into the Map<String, Route>.
     * @param str as String
     */
    private void createRoute(String str)
    {
        List<TrekPart> tParts;
        TrekPart tp;
        Route rte;
        String[] arrParts = str.split("\n");

        //if route is too short or is a known bad actor add to invSet
        if (arrParts.length < 4 || invSet.contains(arrParts[0]))
        {
            invSet.add(arrParts[0]);
        }
        else
        {
            try 
            {
                tParts = new LinkedList<TrekPart>();
                //convert data lines to TrekParts
                for (int ii = 2; ii < arrParts.length - 1; ii++)
                {
                    tp = createTP(arrParts[ii], arrParts[ii+1]);
                    if (tParts.size() > 0)
                    {
                        if (!validateLink(tParts.get(tParts.size()-1), tp))
                        {
                            throw new IllegalArgumentException("TrekParts" +
                                "too far apart");
                        }
                    }
                    tParts.add(tp);
                } 
                
                rte = new Route(arrParts[0], arrParts[1], tParts);
                rteMap.put(arrParts[0], rte);
            } 
            catch (IllegalArgumentException | IndexOutOfBoundsException e) 
            {
                invSet.add(arrParts[0]);
            }
        }
    }

    /**
     * Creates a TrekPart from 2 data lines from route string
     * @param start as String
     * @param end as String
     * @return completed trekpart
     */
    private TrekPart createTP(String start, String end)
    {
        String rteName, rteData;
        
        /*
        * Can't split start on , in case there are ,s in the
        * description/name. Doesn't matter for end as not extracting
        * the description/name
        */
        String[] startParts = extractCoords(start);
        String[] endParts = end.split(",");

        //if start doesn't match at least lat,lon,alt,desc
        if (startParts.length < 4)
        {
            throw new IllegalArgumentException("Route too short");
        }

        //if start line descibes a route
        if (startParts[3].charAt(0) == '*')
        {
            rteName = startParts[3].substring(1,startParts[3].length());
            //if route is known
            if (rteMap.containsKey(rteName))
            {
                return rteMap.get(rteName);
            }
            //if route is known bad actor
            else if(invSet.contains(rteName))
            {
                throw new IllegalArgumentException("Route uses invalid "
                    + "subroute data");
            }
            //if route is not known
            else
            {
                rteData = strMap.get(rteName);
                //if data for unkown route exists
                if (rteData != null)
                {
                    createRoute(strMap.get(rteName));
                    return rteMap.get(rteName);
                }
                //if data for unkown route does not exist
                else
                {
                    invSet.add(rteName);
                }
            }
        }
        //it describes a segment
        else
        {
            try
            {
                return createSegment(startParts, endParts);
            }
            catch(NullPointerException e)
            {
                throw new IllegalArgumentException("Invalid gps " + 
                    "coordinates");
            }
        }
        //captures things that don't meet above criteria, therefore invalid
        throw new IllegalArgumentException("Invalid TrekPart Data");
    }

    /**
     * Extracts the coordinates and description from a string
     * matching the pattern lat,lon,alt,string
     * @param str as String
     * @return array of components
     */
    private String[] extractCoords(String str)
    {
        int com1, com2, com3;
        String[] strRet = new String[4];

        com1 = str.indexOf(',');
        com2 = str.indexOf(',', com1+1);
        com3 = str.indexOf(',', com2+1);
       
        strRet[0] = str.substring(0,com1);
        strRet[1] = str.substring(com1+1,com2);
        strRet[2] = str.substring(com2+1,com3);
        strRet[3] = str.substring(com3+1,str.length());

        return strRet;
    }

    /**
     * Creates a CalculatedTrek from a route object
     * @param route as Route
     * @return flattened route with precalculated distances
     * @throws TrekCalculationException if error occurs in creation
     */
    public CalculatedTrek create(Route route)
        throws TrekCalculationException
    {
        return new CalculatedTrek(route);
    }

    /**
     * Creates a Segment from the provided data
     * @param start as String[]
     * @param end as String[]
     * @return constructed segment
     */
    private Segment createSegment(String[] start, String[] end)
    {
        double lat1, lat2, lon1, lon2, alt1, alt2, dist;
        Waypoint startWP, endWP;
                
        lat1 = Double.parseDouble(start[0]);
        lon1 = Double.parseDouble(start[1]);
        alt1 = Double.parseDouble(start[2]);
        lat2 = Double.parseDouble(end[0]);
        lon2 = Double.parseDouble(end[1]);
        alt2 = Double.parseDouble(end[2]);    
    
        startWP = locFac.create(lat1, lon1, alt1);
        endWP = locFac.create(lat2, lon2, alt2);
        dist = geoUtil.calcMetresDistance(lat1, lon1, lat2, lon2);

        return new Segment(startWP, endWP, dist, alt2 - alt1, start[3]);
    }

    private boolean validateLink(TrekPart tp1, TrekPart tp2)
    {
        Settings settings = Settings.getInstance();
        Waypoint wp1 = tp1.getEnd();
        Waypoint wp2 = tp2.getStart();

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

