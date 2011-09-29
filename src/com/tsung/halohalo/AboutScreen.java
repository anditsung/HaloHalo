package com.tsung.halohalo;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.system.ApplicationDescriptor;

/**
 * PopupScreen that displays information about the application
 */

final class AboutScreen extends PopupScreen
{
    /**
     * Constructor.
     */
    AboutScreen()
    {
        super(new VerticalFieldManager());
        HorizontalFieldManager hfm = new HorizontalFieldManager(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL | Field.FIELD_HCENTER);       
        ApplicationDescriptor appDesc = ApplicationDescriptor.currentApplicationDescriptor();        
        add(new LabelField(appDesc.getName() + " v" + appDesc.getVersion())); // Show App Name and its current Version.
        add(new SeparatorField());
        add(new LabelField("Developed by AndiTsunG")); // Information
        add(new LabelField("komunitasblackberry.com"));
        //add(new LabelField("Special thanks to : Sergey, Dellcorp, Kaskuser"));            
    }

   
    /**
     * Overrides the default.  Closes the popup screen when the Escape key is pressed.
     *
     * @see net.rim.device.api.ui.Screen#keyChar(char,int,int)
     */
    public boolean keyChar(char c, int status, int time)
    {
        if (c == Characters.ESCAPE)  // Close this when user press escape button.
        {
            close();
            return true;
        }
       
        return super.keyChar(c, status, time);
    }
}