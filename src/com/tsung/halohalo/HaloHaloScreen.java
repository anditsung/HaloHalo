package com.tsung.halohalo;

import net.rim.blackberry.api.phone.Phone;
import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.applicationcontrol.ApplicationPermissionsManager;
import net.rim.device.api.media.control.AudioPathControl;
import net.rim.device.api.system.Audio;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class HaloHaloScreen extends MainScreen
{
	private String onOffOption = "Off";
	private String delayOption = "3";
    private PersistentObject pDelayField;
    private ObjectChoiceField delayField;
    private ObjectChoiceField onOffField;
    private CallListener pl;    
	
    /**
     * Creates a new MyScreen object
     */
    public HaloHaloScreen()
    {
    	checkPermission();
    	// com.tsung.autohalo.pDelayField = 0xe3a39a9e32211dfbL
    	pDelayField = PersistentStore.getPersistentObject(0xe3a39a9e32211dfbL);
    	if(pDelayField.getContents() == null) {
    		pDelayField.setContents(delayOption);
    		pDelayField.commit();
    	}
    	// com.tsung.autohalo.pAudioField = 0xef521eb529fd4d8cL
    	//PersistentObject pAudioField = PersistentStore.getPersistentObject(0xef521eb529fd4d8cL);
    	
        // Set the displayed title of the screen       
        setTitle("HaloHalo");
        final String[] answerChoice = { "Off", "On" };
        final String[] delayChoice = { "1", "2", "3", "4", "5" };
        onOffField = new ObjectChoiceField("Auto Answer : ", answerChoice);
       
        onOffField.setChangeListener(new FieldChangeListener() {
        	
        	public void fieldChanged(Field field, int context) {
        		if(field instanceof ObjectChoiceField) {
        			ObjectChoiceField distance = (ObjectChoiceField)field;
        			String getLabel = distance.getLabel();
        			if(getLabel.equals("Auto Answer : ")) {
        				onOffOption = answerChoice[distance.getSelectedIndex()];
        				if(onOffOption.equals("On")) {
        					// add secon before answer
        					
        					delayField = new ObjectChoiceField("Seconds before answer : ", delayChoice);
        					delayField.setSelectedIndex(pDelayField.getContents());
        					delayOption = (String)pDelayField.getContents();
        					
        					// phone listener
        					pl = new CallListener(delayOption);
        					Phone.addPhoneListener(pl);
        					
           					add(delayField);           					
           					delayField.setChangeListener(this);
        				}
        				
        				else {
        					// delete second before answer
        					delete(delayField);
        					
        					// phone listener
        					Phone.removePhoneListener(pl);
        				}
        			}
        			else if (getLabel.equals("Seconds before answer : ")) {
        				// second before answer listener
        				
        				// get user selection
        				String userSelection = delayChoice[distance.getSelectedIndex()];
        				
        				// save to pDelayField
        				pDelayField.setContents(userSelection);
        				pDelayField.commit();        				
        				
        			}        			
        		}        	
        	}
        });
        add(onOffField);
        addMenuItem(_about);
    }
    
    public boolean onSavePrompt() {
    	if(onOffOption.equals("On")) {
    		UiApplication.getUiApplication().requestBackground();
    		return false;
    	}
    	return true;
    }

    private MenuItem _about = new MenuItem("About", 110, 10) {
		public void run() {
			UiApplication.getUiApplication().pushScreen(new AboutScreen());
		}
	};
	
private void checkPermission() {
		
		// Capture the current state of permissions and check against the requirements
		ApplicationPermissionsManager apm = ApplicationPermissionsManager.getInstance();
        ApplicationPermissions original = apm.getApplicationPermissions();
        
        if(original.getPermission(ApplicationPermissions.PERMISSION_INPUT_SIMULATION) == ApplicationPermissions.VALUE_ALLOW &&
        		original.getPermission(ApplicationPermissions.PERMISSION_PHONE) == ApplicationPermissions.VALUE_ALLOW &&
                original.getPermission(ApplicationPermissions.PERMISSION_DEVICE_SETTINGS) == ApplicationPermissions.VALUE_ALLOW &&
                original.getPermission(ApplicationPermissions.PERMISSION_EMAIL) == ApplicationPermissions.VALUE_ALLOW)
             {
                 // All of the necessary permissions are currently available
                 return;
             }
        // Create a permission request for each of the permissions your application
        // needs. Note that you do not want to list all of the possible permission
        // values since that provides little value for the application or the user.  
        // Please only request the permissions needed for your application.
        ApplicationPermissions permRequest = new ApplicationPermissions();
        permRequest.addPermission(ApplicationPermissions.PERMISSION_INPUT_SIMULATION);
        permRequest.addPermission(ApplicationPermissions.PERMISSION_PHONE);
        permRequest.addPermission(ApplicationPermissions.PERMISSION_DEVICE_SETTINGS);
        permRequest.addPermission(ApplicationPermissions.PERMISSION_EMAIL);

        boolean acceptance = ApplicationPermissionsManager.getInstance().invokePermissionsRequest(permRequest);

        if(acceptance)
        {
            // User has accepted all of the permissions
            return;
        }
        else
        {
            // The user has only accepted some or none of the permissions 
            // requested. In this sample, we will not perform any additional 
            // actions based on this information. However, there are several 
            // scenarios where this information could be used. For example,
            // if the user denied networking capabilities then the application 
            // could disable that functionality if it was not core to the 
            // operation of the application.
        }
	}
    
}
