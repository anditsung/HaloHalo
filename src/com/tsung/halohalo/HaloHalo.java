package com.tsung.halohalo;

import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class HaloHalo extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        HaloHalo theApp = new HaloHalo();       
        theApp.enterEventDispatcher();
    }    

    /**
     * Creates a new MyApp object
     */
    public HaloHalo()
    {
        // Push a screen onto the UI stack for rendering.
    	HaloHaloScreen ahs = new HaloHaloScreen();
        pushScreen(ahs);
    }    
}
