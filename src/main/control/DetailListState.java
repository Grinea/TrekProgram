package main.control;

import main.view.View;

/**
* Controller state for route detail display
* 
* @author Owen Frere
*/
public class DetailListState extends State
{

    private View view;
    private Controller con;

    /**
     * Constructor
     * @param con program controller
     * @param view state's view
     */
    public DetailListState(Controller con, View view)
    {
        this.con = con;
        this.view = view;
    }

    /**
     * Displays the state's view
     */
    @Override
    public void initialise()
    {
        view.displayView(null, con.getDetailRoute(), con.getRoutes(),
            con.getLastKnown());
    }
}