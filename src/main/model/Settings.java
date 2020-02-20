package main.model;

import java.text.DecimalFormat;

/**
 * Singleton class representing the settings of the trek route program
 * 
 * @author Owen Frere
 */
public class Settings
{
    private DecimalFormat metresFormat;
    private DecimalFormat coordFormat;
    private double vLimit = 2.0;
    private double hLimit = 10.0;
    private static Settings instance;

    /**
     * Constructor
     */
    private Settings()
    {
        this.metresFormat = new DecimalFormat("#.##");
        this.coordFormat = new DecimalFormat("#.####");
    }

    /**
     * Instantiates the singleton if required and then returns instance
     * @return singleton instance
     */
    public static Settings getInstance()
    {
        if (instance == null)
        {
            return new Settings();
        }
        
        return instance;
    }

    /**
     * Returns DecimalFormat representing program wide metre rounding setting
     * @return metre formatter
     */
    public DecimalFormat getMFormat()
    {
        return metresFormat;
    }

    /**
     * Returns DecimalFormat representing program wide coordinate 
     * rounding setting
     * @return coordinate formatter
     */
    public DecimalFormat getCFormat()
    {
        return coordFormat;
    }

    /**
     * Returns a double that represents the tolerance of vertical
     * comparisons for Waypoint "close enough" checks
     * @return vertical tolerance 
     */
    public double getVLimit()
    {
        return vLimit;
    }

    /**
     * Returns a double that represents the tolerance of horizontal
     * comparisons for Waypoint "close enough" checks
     * @return horizontal tolerance 
     */    
    public double getHLimit()
    {
        return hLimit;
    }
}