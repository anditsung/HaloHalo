package com.tsung.halohalo;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.blackberry.api.phone.AbstractPhoneListener;
import net.rim.blackberry.api.phone.Phone;
import net.rim.blackberry.api.phone.PhoneCall;
import net.rim.device.api.system.Audio;
import net.rim.device.api.system.EventInjector;
import net.rim.device.api.system.EventInjector.KeyCodeEvent;

public class CallListener extends AbstractPhoneListener{
	
	private int oneHundredMs = 0;
	private String ringDelay;
	private KeyCodeEvent answerPhoneDown;
	private KeyCodeEvent answerPhoneUp;
	private PhoneCall phoneCall;
	private Timer timer;

	public CallListener(String delayOption) {
		ringDelay = delayOption;
		answerPhoneDown = new EventInjector.KeyCodeEvent(EventInjector.KeyCodeEvent.KEY_DOWN, (char)17, 0);
		answerPhoneUp = new EventInjector.KeyCodeEvent(EventInjector.KeyCodeEvent.KEY_UP, (char)17, 0);		
	}
	
	public void checkDelay(int callId) {		
		phoneCall = Phone.getCall(callId);
		if(phoneCall != null) {
			timer = new Timer();
			int _delayTime = Integer.parseInt(ringDelay);
			CallListenerTimerTask cltt = new CallListenerTimerTask();
			timer.schedule(cltt, _delayTime * 1000);			
		}
	}
	
	public void callIncoming(int callId) {
		if(Audio.isHeadsetConnected() == true) {
			checkDelay(callId);
		}
	}
	
	public void callDisconnected(int callId) {
		if(timer != null) {
			timer.cancel();			
		}
	}
	
	class CallListenerTimerTask extends TimerTask {
					
		public void run() {
			// TODO Auto-generated method stub			
			EventInjector.invokeEvent(answerPhoneDown);			
		}
		
	}
	
}


