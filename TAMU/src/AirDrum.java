import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import javax.sound.sampled.*;
import javafx.scene.media.*;

class Runner extends Thread {
	public void run(){
		
		InputStreamReader fr = new InputStreamReader(System.in);
		BufferedReader bufReader = new BufferedReader(fr);
		
		StringBuffer cache = new StringBuffer();
		int counter = 0;
		int soundWait = 0;
		int waitCymbal = 0, waitBass = 0, waitSnare = 0;
		double previousG = 0;
		double currentG = 0;
		double delta = 0;
		boolean playSound = false;
		
		//int minTime = 300; //Min time (ms) for each JSON object read,
							//otherwise throw away object
		//long lastTime = System.currentTimeMillis();
		
		  while(true){
		      try {
		          String inputStr = null;
		          // Checks to see if buffered reader is outputting values
		          if((inputStr=bufReader.readLine()) != null) {
		        	  cache.append(inputStr + "\n");
		        	  counter++;
		        	  
		        	  //Each JSON object is 68 lines long
		        	  if (counter == 68) {
		        		  
		        		  /*long cur = System.currentTimeMillis();
		        		  if (cur - lastTime >= minTime) {
		        			  //Allowable
		        			  lastTime = cur;
		        		  }
		        		  else {
		        			  //Ignore this data (too fast)
		        			  counter = 0;
			        		  cache.setLength(0);
			        		  continue;
		        		  }*/
		        		  JSONParser parser = new JSONParser();
		        		  JSONObject o = (JSONObject) parser.parse(cache.toString());
		        		  
		        		  // parses finger data
		        		  double fingerflex1 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(0)));
		        		  double fingerflex2 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(1)));
		        		  double fingerflex3 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(2)));
		        		  double fingerflex4 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(3)));
		        		  double fingerflex5 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(4)));
		        		  
		        		  double fingerpres1 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(0)));
		        		  double fingerpres2 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(1)));
		        		  double fingerpres3 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(2)));
		        		  double fingerpres4 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(3)));
		        		  double fingerpres5 = Double.parseDouble(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(4)));

		        		  
		        		  //checks to see if bass should be played
		        		  boolean isBass = (!(fingerpres2 < 60 ) && !(fingerpres3 < 100)); //  || ((fingerflex1 <= 1010) && (fingerflex2 <= 1010) && (fingerflex3 <= 1010) && (fingerflex4 <= 1010) && (fingerflex5 <= 1010))
		        		  boolean isSnare = ((fingerpres2 < 60) && (fingerpres3 < 100)); // || (!(fingerflex1 <= 1010) && !(fingerflex2 <= 1010) && !(fingerflex3 <= 1010) && !(fingerflex4 <= 1010) && !(fingerflex5 <= 1010))
		        		  boolean isCymbal =  ((fingerpres2 < 60) && !(fingerpres3 < 100)); // || ((fingerflex1 <= 1010) && !(fingerflex2 <= 1010) && (fingerflex3 <= 1010) && (fingerflex4 <= 1010) && (fingerflex5 <= 1010));
		        		  
		        		  
		        		  if (!isCymbal) {
		        			  AirDrum.labels.get("sounds").get(0).setText("-"); 
		        		  }
		        		  if (!isBass) {
		        			  AirDrum.labels.get("sounds").get(1).setText("-"); 
		        		  }
		        		  if (!isSnare) {
		        			  AirDrum.labels.get("sounds").get(2).setText("-"); 
		        		  }
		        		  
		        		  if(isCymbal) {
		        			  if(!playSound && delta == -1 && soundWait == 0) {
			        			  AirDrum.labels.get("sounds").get(0).setText("Cymbal played");
			        			  AirDrum.play("cymbal");
			        			  playSound = true;
			        			  soundWait++;
		        			  } else {
		        				  soundWait++;
		        				  if(soundWait >= 3) {
		        					  soundWait = 0;
				        			  playSound = false;
		        				  }
		        			  }
		        		  } else {
		        			  if(soundWait == 0)
		        				  AirDrum.labels.get("sounds").get(0).setText("");
		        		  }
		        		  
		        		  if(isBass) {
		        			  if(!playSound && delta == -1 && soundWait == 0) {
			        			  AirDrum.labels.get("sounds").get(1).setText("Bass played");
			        			  AirDrum.play("bass");
			        			  playSound = true;
			        			  soundWait++;
		        			  } else {
		        				  soundWait++;
		        				  if(soundWait >= 3) {
		        					  soundWait = 0;
				        			  playSound = false;
		        				  }
		        			  }
		        		  } else {
		        			  if(soundWait == 0)
		        				  AirDrum.labels.get("sounds").get(1).setText("");
		        		  }
		        		  
		        		  if(isSnare) {
		        			  if(!playSound && delta == -1 && soundWait == 0) {
			        			  AirDrum.labels.get("sounds").get(2).setText("Snare played");
			        			  AirDrum.play("snare");
			        			  playSound = true;
			        			  soundWait++;
		        			  } else {
		        				  soundWait++;
		        				  if(soundWait >= 3) {
		        					  soundWait = 0;
				        			  playSound = false;
		        				  }
		        			  }
		        		  } else {
		        			  if(soundWait == 0)
		        				  AirDrum.labels.get("sounds").get(2).setText("");
		        		  }
		        		  if(!isBass && !isCymbal && !isSnare) {
		        			  soundWait = 0;
		        			  playSound = false;
		        		  }

		        		  double temp = 0;
		        		  temp = previousG;
		        		  previousG = currentG;
		        		  currentG = Double.parseDouble(String.valueOf(
								((JSONObject)((JSONObject)((JSONObject)
										o.get("hand"))
										.get("accelerometer")).
										get("g")).get("y")));
		        		  delta = (currentG - previousG);
		        		  if(delta <= 0.08 && delta >= -0.08)
		        			  delta = 0.0;
		        		  if(delta >0.08)
		        			  delta = 1.0;
		        		  if(delta <-0.08)
		        			  delta = -1.0;
		        		 // Gets the all 5 finger flex information
		        		  for (int i = 0; i < 5; i++)
		        		 AirDrum.labels.get("flex").get(i).setText("finger"+ (i + 1)+ ": " + String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(i)));
		        		  
		        		// Gets the all 5 finger pressure information
		        		  for (int i = 0; i < 5; i++)
				        AirDrum.labels.get("pressure").get(i).setText("finger"+ (i + 1)+ ": " + String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(i)));
		        		  
		        		  String[] chars = {"x", "y", "z"};
		        		  String[] hpr = {"heading", "pitch", "roll"};
		        		// Gets the Accelerometer adc information
		        		  for (int i = 0; i < 3; i++)
						AirDrum.labels.get("accelerometer_adc").get(i).setText(chars[i] + ": " + String.valueOf(
								((JSONObject)((JSONObject)((JSONObject)
										o.get("hand"))
										.get("accelerometer")).
										get("adc")).get(chars[i])));

		        		// Gets the Accelerometer delta (change in adc) information
		        		  AirDrum.labels.get("accelerometer_delta").get(0).setText("delta: " + delta);
		        		  
		        		// Gets the Attitude g information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("accelerometer_g").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("accelerometer")).
												get("g")).get(chars[i])));
		        		  
		        		// Gets the Attitude degrees information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("attitude_degrees").get(i).setText(hpr[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("attitude")).
												get("degrees")).get(hpr[i])));

		        		  // Gets the Attitude radians information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("attitude_radians").get(i).setText(hpr[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("attitude")).
												get("radians")).get(hpr[i])));

		        		  // Gets the Gyroscope adc information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("gyroscope_adc").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("gyroscope")).
												get("adc")).get(chars[i])));
		        		  
		        		  // Gets the Gyroscope dps information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("gyroscope_dps").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("gyroscope")).
												get("dps")).get(chars[i])));
		        		  
		        		  // Gets the Magnetometer adc information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("magnetometer_adc").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("magnetometer")).
												get("adc")).get(chars[i])));
		        		  
		        		  // Gets the Magnetometer gauss information
		        		  for (int i = 0; i < 3; i++)
								AirDrum.labels.get("magnetometer_gauss").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("magnetometer")).
												get("gauss")).get(chars[i])));
		        		  //Resets counter
		        		  counter = 0;
		        		  cache.setLength(0);
		        	  }
		          }
		          else {
		              System.out.println("Input ended");
		              break;
		          }
		      }
		      catch (Exception e) {System.out.println("Error: " + e.getMessage());}
		  }
	}	
}
    


public class AirDrum{

	
public static HashMap<String, ArrayList<JLabel>> labels = new HashMap<String, ArrayList<JLabel>>();
public static HashMap<String, Clip> sfx = new HashMap<String, Clip>();

static JFrame frame;
static int x = 0;
static int y = 0;
static int w = 150;
static int h = 30;

// Bolder font labels
public static void addBoldLabel(String s) {
	JLabel j = new JLabel(s);
	Font f = j.getFont();
	j.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
	j.setBounds(new Rectangle(x,y,w,h));
	frame.add(j);
	y += 30;
}

// Regular font labels
public static void addLabel(String s) {
	JLabel j = new JLabel(s);
	j.setBounds(new Rectangle(x,y,w,h));
	frame.add(j);
	y += 30;
}

// ** Important ** Where all visual data is updated
public static void addSet(String setName, int iterations) {
	ArrayList<JLabel>set = new ArrayList<JLabel>();
	for (int i = 0; i < iterations; i++) {
		JLabel j = new JLabel("-");
		j.setBounds(new Rectangle(x,y,w,h));
		frame.add(j);
		set.add(j);
		y += 20;
	}
	y += 40;
	// Setting global variable
	labels.put(setName, set);
}
	
public static void play(String s) {
		Clip clip = sfx.get(s);
		
		clip.stop();
		clip.setFramePosition(0);
		
		//if (clip.getFramePosition() != 0)
		clip.start();
		
}

public static void main (String args[]) {
	
	//Load sound fx
	try {
		File f = new File("bass.wav");
		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(f));
		sfx.put("bass", clip);
		
		f = new File("cymbal.wav");
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(f));
		sfx.put("cymbal", clip);
		
		f = new File("snare.wav");
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(f));
		sfx.put("snare", clip);
		
		//clip.setFramePosition(0);
		//clip.start();
	}catch(Exception e) {System.out.println("err loading soundfx: " + e.getMessage());}
	
	//Create GUI
	frame = new JFrame("Smart Glove Visualizer");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(1000,1000);
	frame.setLayout(null);
	x += 30;
	// Finger flex and pressure data
	addBoldLabel("Fingers");
	addBoldLabel("Flex");
	addSet("flex", 5);
	addBoldLabel("Pressure");
	addSet("pressure", 5);
	
	y = 0;
	x += 275;
	
	// Accelerometer adc/g and Attitude degrees/radians data
	addBoldLabel("Hands");
	addBoldLabel("Accelerometer");
	addLabel("adc");
	addSet("accelerometer_adc", 3);
	addSet("accelerometer_delta", 1);
	addLabel("g");
	addSet("accelerometer_g", 3);
	addBoldLabel("Attitude");
	addLabel("degrees");
	addSet("attitude_degrees", 3);
	addLabel("radians");
	addSet("attitude_radians", 3);
	
	y = 30;
	x += 275;
	
	// Gyroscope adc/dps and Magnetometer adc/gauss data
	addBoldLabel("Gyroscope");
	addLabel("adc");
	addSet("gyroscope_adc", 3);
	addLabel("dps");
	addSet("gyroscope_dps", 3);
	addBoldLabel("Magnetometer");
	addLabel("adc");
	addSet("magnetometer_adc", 3);
	addLabel("gauss");
	addSet("magnetometer_gauss", 3);
	
	
	y = 30;
	x += 275;

	// Sound to play
	addBoldLabel("Sounds");
	addSet("sounds", 3);
	
	// Sets visual table to true
	frame.setVisible(true);
	
	// Start the thread
	Runner r = new Runner();
	r.start();
	
}


}


