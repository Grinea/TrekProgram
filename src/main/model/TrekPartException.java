package main.model;

/**
 * Excpetion representing a failure during creation of a TrekPart
 * 
 * @author Owen Frere
 */
public class TrekPartException extends Exception
{
    /**
     * Constructor
     * @param errorMessage as String
     */
    public TrekPartException(String errorMessage)
    {
        super(errorMessage);
    }
}