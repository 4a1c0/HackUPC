/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;



class SampleListener extends Listener {

	public Robot mouse;
	
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        //controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);

        controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
    	try { 
    		mouse = new Robot();
    	} catch (Exception e) {}
    	
    	
    	
        Frame frame = controller.frame();
        Frame anterior = controller.frame(1);
       
        
        
        InteractionBox pla = frame.interactionBox();
        for(Finger dit : frame.fingers()) {
        	for(Finger dit2 : anterior.fingers()){
        		
        	
        		if ( dit.type() == Finger.Type.TYPE_INDEX) {
        			Vector ditPos = dit.stabilizedTipPosition();
        			Vector normPosDit = pla.normalizePoint(ditPos);
        			Dimension pantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        			mouse.mouseMove((int) (pantalla.width * normPosDit.getX()), (int) (pantalla.height - (pantalla.height * normPosDit.getY())));
        			if (dit2.type() == Finger.Type.TYPE_THUMB){
        				if (dit.isExtended() && dit2.isExtended()){
        					mouse.mousePress(InputEvent.BUTTON1_MASK);
        					mouse.mouseRelease(InputEvent.BUTTON1_MASK);
        					try { 
                        		Thread.sleep(60);
                        	} catch (Exception e) {}
        				}
        			}
        		}
        		
        	}

        }
        


        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);

                    // Calculate clock direction using the angle between circle normal and pointable
                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
                        // Sentit agulles del rellotge
                        mouse.mouseWheel(1);
                    	try { 
                    		Thread.sleep(60);
                    	} catch (Exception e) {}
                    } else {
                    	mouse.mouseWheel(-1);
                    	try { 
                    		Thread.sleep(60);
                    	} catch (Exception e) {}
                    }

                    break;
               
                case TYPE_SCREEN_TAP:
                    mouse.mousePress(InputEvent.BUTTON1_MASK);
                    mouse.mouseRelease(InputEvent.BUTTON1_MASK);

                    break;
                
                default:
                    System.out.println("Unknown gesture type.");
                    break;
            }
        }

        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
            System.out.println();
        }
    }
}

class LeapMotion {
    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
 
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);}
}




    

