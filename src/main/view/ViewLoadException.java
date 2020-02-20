package main.view;
/**
 * Exception representing a failure in the factory loading
 * views
 * 
 * @author Owen Frere
 */
public class ViewLoadException extends Exception
{
    /**
     * Constructor
     * @param errorMessage as String
     */
    public ViewLoadException(String errorMessage)
    {
        super(errorMessage);
    }
}