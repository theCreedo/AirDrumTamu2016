import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

class Runner extends Thread {
	public void run(){
		
		InputStreamReader fr = new InputStreamReader(System.in);
		/*FileReader fr = null;
		try {
			fr = new FileReader(new File("hand_json"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		BufferedReader bufReader = new BufferedReader(fr);
		
		StringBuffer cache = new StringBuffer();
		int counter = 0;
		
		  while(true){
		      try {
		          String inputStr = null;
		          if((inputStr=bufReader.readLine()) != null) {
		              //System.out.println(inputStr);
		        	  cache.append(inputStr + "\n");
		        	  counter++;
		        	  
		        	  if (counter == 68) { //Each JSON object is 68 lines long
		        		  //System.out.println("counter == 68");
		        		  JSONParser parser = new JSONParser();
		        		  JSONObject o = (JSONObject) parser.parse(cache.toString());
		        		//  JSONArray array = (JSONArray) o;
		        		//  JSONObject obj = (JSONObject) array.get(0); //First one (and only one)
		        		  
		        		  
		        		  //System.out.println("works");
		        		  
		        		  
		        		  for (int i = 0; i < 5; i++)
		        		 Shit.labels.get("flex").get(i).setText(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("flex")).get(i)));
		        		  
		        		  for (int i = 0; i < 5; i++)
				        Shit.labels.get("pressure").get(i).setText(String.valueOf(((JSONArray)((JSONObject)o.get("fingers")).get("pressure")).get(i)));
		        		  
		        		  String[] chars = {"x", "y", "z"};
		        		  String[] hpr = {"heading", "pitch", "roll"};
		        		  for (int i = 0; i < 3; i++)
						Shit.labels.get("accelerometer_adc").get(i).setText(chars[i] + ": " + String.valueOf(
								((JSONObject)((JSONObject)((JSONObject)
										o.get("hand"))
										.get("accelerometer")).
										get("adc")).get(chars[i])));
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("accelerometer_g").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("accelerometer")).
												get("g")).get(chars[i])));
		        		  
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("attitude_degrees").get(i).setText(hpr[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("attitude")).
												get("degrees")).get(hpr[i])));
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("attitude_radians").get(i).setText(hpr[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("attitude")).
												get("radians")).get(hpr[i])));
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("gyroscope_adc").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("gyroscope")).
												get("adc")).get(chars[i])));
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("gyroscope_dps").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("gyroscope")).
												get("dps")).get(chars[i])));
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("magnetometer_adc").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("magnetometer")).
												get("adc")).get(chars[i])));
		        		  
		        		  for (int i = 0; i < 3; i++)
								Shit.labels.get("magnetometer_gauss").get(i).setText(chars[i] + ": " + String.valueOf(
										((JSONObject)((JSONObject)((JSONObject)
												o.get("hand"))
												.get("magnetometer")).
												get("gauss")).get(chars[i])));
		        		  
		        		  counter = 0;
		        		  cache.setLength(0);
		        		  //break;
		        	  }
		          }
		          else {
		              System.out.println("Input ended");
		              break;
		              
		          }
		      }
		      catch (Exception e) {System.out.println("Error: " + e.getMessage());}
			  /*System.out.println("balls");
			  try {
				  Thread.sleep(250);
			  }
			  catch(Exception e) {}*/
		  }
		  
		  //System.out.println(cache);
	}
	
		
}
    


public class Shit{

	
public static HashMap<String, ArrayList<JLabel>> labels = new HashMap<String, ArrayList<JLabel>>();
static JFrame frame;
static int x = 0;
static int y = 0;
static int w = 150;
static int h = 30;


public static void addBoldLabel(String s) {
	JLabel j = new JLabel(s);
	Font f = j.getFont();
	j.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
	j.setBounds(new Rectangle(x,y,w,h));
	frame.add(j);
	y += 30;
}

public static void addLabel(String s) {
	JLabel j = new JLabel(s);
	j.setBounds(new Rectangle(x,y,w,h));
	frame.add(j);
	y += 30;
}

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
	labels.put(setName, set);
}
	
public static void main (String args[]) {
  /*InputStreamReader isReader = new InputStreamReader(System.in);
  BufferedReader bufReader = new BufferedReader(isReader);
  while(true){
      try {
          String inputStr = null;
          if((inputStr=bufReader.readLine()) != null) {
              System.out.println(inputStr);
          }
          else {
              System.out.println("Input ended");
              break;
              
          }
      }
      catch (Exception e) {
          //do nothing
      }
  }*/
	
	//Create GUI
	frame = new JFrame("Smart Glove Visualizer");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(1000,1000);
	frame.setLayout(null);
	
	addBoldLabel("Fingers");
	addBoldLabel("Flex");
	addSet("flex", 5);
	addBoldLabel("Pressure");
	addSet("pressure", 5);
	
	y = 0;
	x += 300;
	
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
	

	
	frame.setVisible(true);
	
	Runner r = new Runner();
	r.start();
	
}


}


