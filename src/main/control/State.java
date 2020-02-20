package main.control;

import main.view.View;

/**
 * Abstract class for controller's state object
 * 
 * @author Owen Frere
 */
public abstract class State
{

    View view;

    /**
     * Runs state
     */
    public abstract void initialise();
    
    /**
     * Displays provided on the view associated view
     * @param err
     */
    public void displayError(String err)
    {
        view.displayError(err);
    }

    /**
     * Deconstructs the state and removes it from observer lists
     */
    public void deconstruct()
    {
    }

}