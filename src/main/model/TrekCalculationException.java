package main.model;

/**
 * Excpetion representing a failure during conversion of 
 * route to a Calculatedtrek
 * 
 * @author Owen Frere
 */
public class TrekCalculationException extends Exception
{

    /**
     * Constructor
     * @param errorMessage as String
     */
    public TrekCalculationException(String errorMessage)
    {
        super(errorMessage);
    }
}