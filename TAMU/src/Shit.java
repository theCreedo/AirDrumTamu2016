import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

class Runner extends Thread {
	public void run(){
		
		InputStreamReader fr = new InputStreamReader(System.in);
		BufferedReader bufReader = new BufferedReader(fr);
		
		StringBuffer cache = new StringBuffer();
		int counter = 0;
		
		  while(true){
		      try {
		          String inputStr = null;
		          // Checks to see if buffered reader is outputting values
		          if((inputStr=bufReader.readLine()) != null) {
		        	  cache.append(inputStr + "\n");
		        	  counter++;
		        	  
		        	  //Each JSON object is 68 lines long
		        	  if (counter == 68) {
		        		  JSONParser parser = new JSONParser();
		        		  JSONObject o = (JSONObject) parser.parse(cache.toString());

		        		 // Gets the all 5 finger flex information
		        		  for (int i = 0; i < 5; i++)
		        		 Shit.labels.get("flex").get(i).setText(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(i)));
		        		  
		        		// Gets the all 5 finger pressure information
		        		  for (int i = 0; i < 5; i++)
				        Shit.labels.get("pressure").get(i).setText(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(i)));
		        		  
		        		  String[] chars = {"x", "y", "z"};
		        		  String[] hpr = {"heading", "pitch", "roll"};
		        		// Gets the Accelerometer adc information
		        		  for (int i = 0; i < 3; i++)
						Shit.labels.get("accelerometer_adc").get(i).setText(chars[i] + ": " + String.valueOf(
								((JSONObject)((JSONObject)((JSONObject)
										o.get("hand"))
										.get("accelerometer")).
										get("adc")).get(chars[i])));
		        		  
		        		// Gets the Attitude g information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("accelerometer_g").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("accelerometer")).
												get("g")).get(chars[i])));
		        		  
		        		// Gets the Attitude degrees information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("attitude_degrees").get(i).setText(hpr[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("attitude")).
												get("degrees")).get(hpr[i])));

		        		  // Gets the Attitude radians information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("attitude_radians").get(i).setText(hpr[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("attitude")).
												get("radians")).get(hpr[i])));

		        		  // Gets the Gyroscope adc information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("gyroscope_adc").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("gyroscope")).
												get("adc")).get(chars[i])));
		        		  
		        		  // Gets the Gyroscope dps information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("gyroscope_dps").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("gyroscope")).
												get("dps")).get(chars[i])));
		        		  
		        		  // Gets the Magnetometer adc information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("magnetometer_adc").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("magnetometer")).
												get("adc")).get(chars[i])));
		        		  
		        		  // Gets the Magnetometer gauss information
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("magnetometer_gauss").get(i).setText(chars[i] + ": " + String.valueOf(
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
    


public class Shit{

	
public static HashMap<String, ArrayList<JLabel>> labels = new HashMap<String, ArrayList<JLabel>>();
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
	
public static void main (String args[]) {
	//Create GUI
	frame = new JFrame("Smart Glove Visualizer");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(1000,1000);
	frame.setLayout(null);
	
	// Finger flex and pressure data
	addBoldLabel("Fingers");
	addBoldLabel("Flex");
	addSet("flex", 5);
	addBoldLabel("Pressure");
	addSet("pressure", 5);
	
	y = 0;
	x += 300;
	
	// Accelerometer adc/g and Attitude degrees/radians data
	addBoldLabel("Hands");
	addBoldLabel("Accelerometer");
	addLabel("adc");
	addSet("accelerometer_adc", 3);
	addLabel("g");
	addSet("accelerometer_g", 3);
	addBoldLabel("Attitude");
	addLabel("degrees");
	addSet("attitude_degrees", 3);
	addLabel("radians");
	addSet("attitude_radians", 3);
	
	y = 30;
	x += 300;
	
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
	

	// Sets visual table to true
	frame.setVisible(true);
	
	// Start the thread
	Runner r = new Runner();
	r.start();
	
}


}


