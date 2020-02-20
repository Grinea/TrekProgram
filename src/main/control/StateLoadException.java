package main.control;

/**
 * Excpetion representing a failure during state initialising
 * 
 * @author Owen Frere
 */
public class StateLoadException extends RuntimeException
{
    /**
     * Constructor
     * @param errorMessage as String
     */
    public StateLoadException(String errorMessage)
    {
        super(errorMessage);
    }
}