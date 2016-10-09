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

import graph.interfacee;

import java.awt.event.InputEvent;

class SampleListener extends Listener {

   
    public static final int FRAMES = 200;

    Cord[] clau = new Cord[FRAMES];
    Cord[] coords = new Cord[FRAMES];
    int clauc = 0; // Clau Correcte
    public static interfacee pantalla;
	boolean recording = false;
	int i = 0;
	boolean clauB = false;
	float n = 40;
	
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");

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

    
        Frame frame = controller.frame();
        
        

        	for(Hand ma : frame.hands()){
        	

        	//System.out.println("Introduir ma al centre");
        	
        		if (ma.isRight()){
        		
        			Cord cord = new Cord(ma.palmPosition().get(0), ma.palmPosition().get(1));
        			//System.out.println("X: " + cord.x + "Y: " + cord.y);
        		
        			
        		
        				if (!cord.isCenter() && !recording){
        					pantalla.escriureText("Ready");
        					pantalla.posarGris();
        				
        			
        				if(cord.x < -20){
        				pantalla.calibrar(false, true, false, false);
        				}
        				
        				if(cord.x > 20){
        				pantalla.calibrar(false, false, false, true);
        				}
        				
        				if(cord.y < 130){
        				pantalla.calibrar(true, false, false, false);
        				}
        				
        				if(cord.y > 170){
        				pantalla.calibrar(false, false, true, false);
        				}
        					
        				}
        				if(cord.isCenter() && !recording){
        					pantalla.calibrar(false, false, false, false);
        					pantalla.escriureText("Ready");
        					pantalla.posarVerd();
        						recording = true;
        	        			try {
        							Thread.sleep(1000);
        						} catch (InterruptedException e) {
        							// TODO Auto-generated catch block
        							e.printStackTrace();
        					}
        						

        					}
        				if (!cord.isCenter() && recording){
        					// groc
        					
        					pantalla.posarGroc();
        					pantalla.escriureText("Recording");
        			
        					if (i < FRAMES){
        						coords[i] = cord;
        						i++;
        					}
        				}
        					//System.out.println(i);
        				if (i == FRAMES-1){
        					recording = false;
        						i = 0;
        						if(!clauB){
        							pantalla.escriureText("Saving...");
        							for(int j = 0; j < FRAMES; j++){
        								clau[j]=coords[j];
        							}
        							try {
        								Thread.sleep(600);
        							} catch (InterruptedException e) {
									// TODO Auto-generated catch block
        								e.printStackTrace();
        							}
        						
        							clauB = true;
        						}
        						

        						
        					for(int c = 0; c < FRAMES-1; c++){
        						
        			    		if(coords[c].x < (clau[c].x+n) && coords[c].x > (clau[c].x-n)){
        			    			if (coords[c].y < (clau[c].y+n) && coords[c].y > (clau[c].y-n)){
        			    				clauc++;
        			    			}
        			    		}
        					}
        					
        					System.out.println(clauc);
        					
        					if (clauc > FRAMES-20){
    					
        						pantalla.escriureText("Correct!");
    					
        							pantalla.posarVerd();
        							try {
										Thread.sleep(600);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								recording = false;
								clauc = 0;
        					}

        					else if(clauc < FRAMES-20){
        						pantalla.escriureText("Error");
        						pantalla.posarVermell();
        						try {
									Thread.sleep(600);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();}
        						recording = false;
        						clauc = 0;
        							}
        					}
        						

        				}
        		}
        	
        		
        		
        	
        		
   
        		if (!frame.hands().isEmpty()) {
        			//System.out.println();
        		}
        	}
    }
    




 class LeapMotion {
    public static void main(String[] args) {
        // Create a sample listener and controller
    	SampleListener.pantalla = new interfacee();
    	SampleListener.pantalla.setVisible(true);
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
        controller.removeListener(listener);
        }
}







    

